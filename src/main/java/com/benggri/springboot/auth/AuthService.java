package com.benggri.springboot.auth;

import com.benggri.springboot.auth.vo.LoginReqVo;
import com.benggri.springboot.auth.vo.LoginResVo;
import com.benggri.springboot.auth.vo.SignupReqVo;
import com.benggri.springboot.comm.vo.CommResponseVo;
import com.benggri.springboot.comm.vo.CommResultVo;
import com.benggri.springboot.comm.vo.TokenVo;
import com.benggri.springboot.config.jwt.TokenProvider;
import com.benggri.springboot.exception.BadRequestException;
import com.benggri.springboot.exception.InternalServerErrorException;
import com.benggri.springboot.member.MemberService;
import com.benggri.springboot.member.vo.MemberVo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.benggri.springboot.comm.util.StringUtil.isEmptyObj;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;
    private final AuthDao authDao;

    public ResponseEntity signup(SignupReqVo signupReqVo) {
        MemberVo memberVoReq = signupReqVo.toMember(passwordEncoder);
        MemberVo checkMemberIdResult = memberService.checkMember(memberVoReq);
        if ( !isEmptyObj(checkMemberIdResult) ) {
            throw new BadRequestException("이미 가입되어 있는 사용자입니다");
        }

        if ( memberService.createMember(memberVoReq) > 0 ) {
            return CommResponseVo.builder().body("가입 완료되었습니다").build().ok();
        } else {
            throw new InternalServerErrorException("가입에 실패하였습니다 잠시후 다시 시도해주세요");
        }
    }

    public ResponseEntity login(LoginReqVo loginReqVo) {
        UsernamePasswordAuthenticationToken authenticationToken = loginReqVo.toAuthentication();
        Authentication authentication = null;
        try {
            authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        } catch (Exception e) {
            throw new BadRequestException("로그인 정보가 일치하지 않습니다");
        }
        CommResultVo<MemberVo> memberResult = memberService.getMemberByMemberId(loginReqVo.toMember());
        MemberVo dbMember = memberResult.getData();
        TokenVo tokenVo = tokenProvider.generateEntityToken(authentication, dbMember);
        return CommResponseVo.builder()
                             .body(LoginResVo.builder()
                                             .memberIdx(dbMember.getMemberIdx())
                                             .memberId(loginReqVo.getMemberId())
                                             .memberName(dbMember.getMemberName())
                                             .accessToken(tokenVo.getAccessToken())
                                             .refreshToken(tokenVo.getRefreshToken())
                                             .build())
                             .build()
                             .ok();
    }


}
