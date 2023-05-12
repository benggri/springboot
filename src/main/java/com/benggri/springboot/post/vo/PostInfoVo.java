package com.benggri.springboot.post.vo;


import com.benggri.springboot.comm.vo.CommPaginationReqVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description="게시글 좋아요/싫어요 처리 VO")
public class PostInfoVo extends CommPaginationReqVo {

    @Schema(description="게시글 table PK idx", example="1")
    private long postIdx;
    @Schema(description="회원 table PK idx", example="1")
    private long memberIdx;

}
