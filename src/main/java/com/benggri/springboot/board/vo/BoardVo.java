package com.benggri.springboot.board.vo;

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
@Schema(description="게시판 VO")
public class BoardVo extends CommPaginationReqVo {

    @Schema(description="게시판 table PK idx", example="1")
    private long boardIdx;
    @Schema(description="게시판 table 컬럼 게시판_이름", example="텍스트", maxLength=50)
    private String boardName;
    @Schema(description="게시판 table 컬럼 게시판_설명", example="텍스트")
    private String boardDesc;
    @Schema(description="게시판 table 컬럼 게시판_설명", example="Y", maxLength=1)
    private String useYn;

}
