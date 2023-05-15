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
@Schema(description="게시글 VO")
public class PostVo extends CommPaginationReqVo {

    @Schema(description="게시글 table PK idx", example="1")
    private long postIdx;
    @Schema(description="게시판 table PK idx", example="1")
    private long boardIdx;
    @Schema(description="게시판 table 컬럼 제목", example="제목")
    private String title;
    @Schema(description="게시판 table 컬럼 내용", example="내용")
    private String contents;
    @Schema(description="회원 table PK idx", example="1")
    private long writerIdx;
    @Schema(description="회원 table 컬럼 회원_ID", example="string")
    private String memberId;
    @Schema(description="회원 table 컬럼 회원_이름", example="string")
    private String memberName;
    @Schema(description="회원 table 컬럼 닉네임", example="string")
    private String nickname;
    @Schema(description="게시판 table 컬럼 작성일시", example="20121212235959")
    private String writeDate;
    @Schema(description="게시판 table 컬럼 상태코드", example="1000")
    private String sttsCd;
    @Schema(description="게시판_정보 table 컬럼 조회수", example="0")
    private long viewCnt;
    @Schema(description="게시판_정보 table 컬럼 좋아요수", example="0")
    private long likeCnt;
    @Schema(description="게시판_정보 table 컬럼 싫어요수", example="0")
    private long unlikeCnt;

    public PostInfoVo toPostInfoVo() {
        return PostInfoVo.builder().postIdx(this.postIdx).memberIdx(this.writerIdx).build();
    }
}
