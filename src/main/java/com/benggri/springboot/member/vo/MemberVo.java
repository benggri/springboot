package com.benggri.springboot.member.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.benggri.springboot.comm.vo.CommPaginationReqVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description="멤버 VO")
public class MemberVo extends CommPaginationReqVo {

    @Schema(description="회원 table PK idx", example="1")
    private long memberIdx;
    @Schema(description="회원 table 컬럼 member_id", example="string")
    private String memberId;
    @JsonIgnore
    @Schema(description="회원 table 컬럼 member_password", example="string")
    private String memberPassword;
    @Schema(description="회원 table 컬럼 member_name", example="string")
    private String memberName;

}
