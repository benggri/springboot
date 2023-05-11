package com.benggri.springboot.auth.vo;

import com.benggri.springboot.member.vo.MemberVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "회원 가입 요청 VO")
public class SignupReqVo {


    @Schema(description="회원 가입 요청 회원 ID", required=true)
    private String memberId;
    @Schema(description="회원 가입 요청 회원 비밀번호", required=true)
    private String memberPassword;
    @Schema(description="회원 가입 요청 회원 이름", required=true)
    private String memberName;
    @Schema(description="회원 가입 요청 회원 이름", required=true)
    private String nickname;

    public MemberVo toMember(PasswordEncoder passwordEncoder) {
        return MemberVo.builder()
                       .memberId(this.memberId)
                       .memberName(this.memberName)
                       .memberPassword(passwordEncoder.encode(this.memberPassword))
                       .nickname(this.nickname)
                       .build();
    }
}
