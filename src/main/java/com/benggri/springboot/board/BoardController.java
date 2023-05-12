package com.benggri.springboot.board;

import com.benggri.springboot.auth.vo.CustomUserDetailsVo;
import com.benggri.springboot.board.vo.BoardVo;
import com.benggri.springboot.comm.vo.CommResponseVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Tag(name="board-controller", description="게시판 관련 API")
@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @Operation(summary = "게시판 목록 조회 API", description = "게시판 목록 조회 API")
    @GetMapping("/boards")
    public ResponseEntity<BoardVo> getBoards(
        HttpServletRequest request,
        @AuthenticationPrincipal CustomUserDetailsVo customUserDetailsVo,
        @Parameter(name="board_name" , required=false, description="공지사항 테이블 제목") @RequestParam(name="board_name" , required=false) String boardName,
        @Parameter(name="pageNo"     , required=false, description="현재 페이지 번호") @RequestParam(name="pageNo", required=false, defaultValue="1") int pageNo,
        @Parameter(name="limit"      , required=false, description="조회할 갯수 기본 5개") @RequestParam(name="limit" , required=false, defaultValue="5") int limit
    ) {
        return CommResponseVo.builder()
                             .resultVo(boardService.getBoards(BoardVo.builder().boardName(boardName).pageNo(pageNo).limit(limit).build()))
                             .build()
                             .toResponseEntity();
    }

    @Operation(summary = "게시판 상세 조회 API", description = "게시판 상세 조회 API")
    @GetMapping("/board/{board_idx}")
    public ResponseEntity<BoardVo> getBoard(
        HttpServletRequest request,
        @AuthenticationPrincipal CustomUserDetailsVo customUserDetailsVo,
        @Parameter(name="board_idx", required=true, description="조회할 게시판_IDX") @PathVariable("board_idx") long board_idx
    ) {
        return CommResponseVo.builder()
                             .resultVo(boardService.getBoard(BoardVo.builder().boardIdx(board_idx).build()))
                             .build()
                             .toResponseEntity();
    }

    @Operation(summary = "게시판 생성 API", description = "게시판 데이터 생성 API")
    @PostMapping("/board")
    public ResponseEntity<BoardVo> createBoard(
        HttpServletRequest request,
        @AuthenticationPrincipal CustomUserDetailsVo customUserDetailsVo,
        @RequestBody BoardVo boardVo
    ) {
        return CommResponseVo.builder()
                             .resultVo(boardService.createBoard(boardVo))
                             .build()
                             .toResponseEntity();
    }

    @Operation(summary = "게시판 수정 API", description = "게시판 데이터 수정 API")
    @PutMapping("/board/{board_idx}")
    public ResponseEntity<BoardVo> updateBoard(
        HttpServletRequest request,
        @AuthenticationPrincipal CustomUserDetailsVo customUserDetailsVo,
        @Parameter(name="board_idx", required=true, description="조회할 게시판_IDX") @PathVariable("board_idx") long board_idx,
        @RequestBody BoardVo boardVo
    ) {
        boardVo.setBoardIdx(board_idx);
        return CommResponseVo.builder()
                             .resultVo(boardService.updateBoard(boardVo))
                             .build()
                             .toResponseEntity();
    }

    @Operation(summary = "게시판 삭제 API", description = "게시판 데이터 삭제 API")
    @DeleteMapping("/board/{board_idx}")
    public ResponseEntity<BoardVo> deleteBoard(
        HttpServletRequest request,
        @AuthenticationPrincipal CustomUserDetailsVo customUserDetailsVo,
        @Parameter(name="board_idx", required=true, description="조회할 게시판_IDX") @PathVariable("board_idx") long board_idx
    ) {
        return CommResponseVo.builder()
                             .resultVo(boardService.deleteBoard(BoardVo.builder().boardIdx(board_idx).build()))
                             .build()
                             .toResponseEntity();
    }

}
