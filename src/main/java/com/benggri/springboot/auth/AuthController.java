package com.benggri.springboot.auth;

import com.benggri.springboot.auth.vo.LoginReqVo;
import com.benggri.springboot.auth.vo.LoginResVo;
import com.benggri.springboot.auth.vo.SignupReqVo;
import com.benggri.springboot.comm.vo.CommResponseVo;
import com.benggri.springboot.exception.BadRequestException;
import com.benggri.springboot.member.MemberService;
import com.benggri.springboot.member.vo.MemberVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static com.benggri.springboot.comm.util.StringUtil.isEmptyObj;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    private final MemberService memberService;

    @Operation(summary = "회원 가입 시 ID 중보체크를 위한 회원 ID 기반 정보 조회 API", description = "테이블 member_info 의 유니크 키인 member_id 를 패스로 전달받아 멤버 정보를 조회합니다")
    @GetMapping("/member/{member_id}")
    public ResponseEntity<MemberVo> getMemberByMemberId(
        HttpServletRequest request,
        @PathVariable("member_id") String member_id
    ) {
        return CommResponseVo.builder()
                             .body(memberService.getMemberByMemberId(MemberVo.builder().memberId(member_id).build()))
                             .build()
                             .ok();
    }

    @Operation(summary = "회원 가입 API", description = "사용자 ID, 비밀번호, 이름을 Table 에 등록 후 로그인할 수 있게 한다")
    @PostMapping("signup")
    public ResponseEntity<String> signup(
        HttpServletRequest request,
        @Parameter(name="signupReqVo", required=true, description="회원가입을 위한 사용자 ID, 비밀번호, 이름") @RequestBody SignupReqVo signupReqVo
    ) {
        return authService.signup(signupReqVo);
    }

    @Operation(summary = "회원 로그인 API", description = "사용자 ID, 비밀번호를 전달 받아 해당 사용자가 등록되어 있는지 확인 후 JWT 토큰을 발급한다")
    @PostMapping("login")
    public ResponseEntity<LoginResVo> login(
        HttpServletRequest request,
        @Parameter(name="loginReqVo", required=true, description="로그인을 위한 사용자 ID, 비밀번호") @RequestBody LoginReqVo loginReqVo
    ) {
        if ( isEmptyObj(loginReqVo.getMemberId()) || isEmptyObj(loginReqVo.getMemberPassword()) ) throw new BadRequestException("필수입력값을 확인하세요");

        return authService.login(loginReqVo);
    }

}
