package com.benggri.springboot.post;


import com.benggri.springboot.auth.vo.CustomUserDetailsVo;
import com.benggri.springboot.comm.vo.CommResponseVo;
import com.benggri.springboot.exception.BadRequestException;
import com.benggri.springboot.post.vo.PostVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Tag(name="post-controller", description="게시글 관련 API")
@RestController
@RequestMapping("board")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @Operation(summary = "게시판 하위 게시글 목록 조회 API", description = "게시판 하위 게시글 목록 조회 API")
    @GetMapping("{board_idx}/posts")
    public ResponseEntity<PostVo> getPosts(
        HttpServletRequest request,
        @AuthenticationPrincipal CustomUserDetailsVo customUserDetailsVo,
        @Parameter(name="board_idx", required=true, description="조회할 게시판_IDX") @PathVariable("board_idx") long board_idx,
        @Parameter(name="pageNo"     , required=false, description="현재 페이지 번호") @RequestParam(name="pageNo", required=false, defaultValue="1") int pageNo,
        @Parameter(name="limit"      , required=false, description="조회할 갯수 기본 5개") @RequestParam(name="limit" , required=false, defaultValue="5") int limit
    ) {
        if (board_idx < 1) throw new BadRequestException("존재하지 않는 게시판 정보입니다");
        return CommResponseVo.builder()
                             .resultVo(postService.getPosts(PostVo.builder().boardIdx(board_idx).pageNo(pageNo).limit(limit).build()))
                             .build()
                             .toResponseEntity();
    }

    @Operation(summary = "게시글 상세 조회 API", description = "게시글 상세 조회 API")
    @GetMapping("{board_idx}/post/{post_idx}")
    public ResponseEntity<PostVo> getPost(
        HttpServletRequest request,
        @AuthenticationPrincipal CustomUserDetailsVo customUserDetailsVo,
        @Parameter(name="board_idx", required=true, description="조회할 게시판_IDX") @PathVariable("board_idx") long board_idx,
        @Parameter(name="post_idx", required=true, description="조회할 게시글_IDX") @PathVariable("post_idx") long post_idx
    ) {
        if (board_idx < 1 || post_idx < 1) throw new BadRequestException("존재하지 않는 게시글 정보입니다");
        return CommResponseVo.builder()
                             .resultVo(postService.getPost(PostVo.builder().boardIdx(board_idx).postIdx(post_idx).build()))
                             .build()
                             .toResponseEntity();
    }

    @Operation(summary = "게시글 생성 API", description = "게시글 생성 API")
    @PostMapping("{board_idx}/post")
    public ResponseEntity<PostVo> createPost(
        HttpServletRequest request,
        @AuthenticationPrincipal CustomUserDetailsVo customUserDetailsVo,
        @Parameter(name="board_idx", required=true, description="게시글 생성할 게시판_IDX") @PathVariable("board_idx") long board_idx,
        @RequestBody PostVo postVo
    ) {
        if (board_idx < 1) throw new BadRequestException("존재하지 않는 게시판 정보입니다");
        postVo.setBoardIdx(board_idx);
        postVo.setWriterIdx(customUserDetailsVo.getMemberIdx());

        return CommResponseVo.builder()
                             .resultVo(postService.createPost(postVo))
                             .build()
                             .toResponseEntity();
    }

    @Operation(summary = "게시글 수정 API", description = "게시글 수정 API")
    @PutMapping("{board_idx}/post")
    public ResponseEntity<PostVo> updatePost(
        HttpServletRequest request,
        @AuthenticationPrincipal CustomUserDetailsVo customUserDetailsVo,
        @Parameter(name="board_idx", required=true, description="조회할 게시판_IDX") @PathVariable("board_idx") long board_idx,
        @RequestBody PostVo postVo
    ) {
        if (board_idx < 1) throw new BadRequestException("존재하지 않는 게시판 정보입니다");
        postVo.setBoardIdx(board_idx);
        postVo.setWriterIdx(customUserDetailsVo.getMemberIdx());

        return CommResponseVo.builder()
                             .resultVo(postService.updatePost(postVo))
                             .build()
                             .toResponseEntity();
    }

    @Operation(summary = "게시글 삭제 API", description = "게시글 삭제 API")
    @DeleteMapping("{board_idx}/post/{post_idx}")
    public ResponseEntity<PostVo> deletePost(
        HttpServletRequest request,
        @AuthenticationPrincipal CustomUserDetailsVo customUserDetailsVo,
        @Parameter(name="board_idx", required=true, description="조회할 게시판_IDX") @PathVariable("board_idx") long board_idx,
        @Parameter(name="post_idx", required=true, description="조회할 게시글_IDX") @PathVariable("post_idx") long post_idx
    ) {
        if (board_idx < 1 || post_idx < 1) throw new BadRequestException("존재하지 않는 게시글 정보입니다");

        return CommResponseVo.builder()
                             .resultVo(postService.deletePost(PostVo.builder()
                                                                    .boardIdx(board_idx)
                                                                    .postIdx(post_idx)
                                                                    .writerIdx(customUserDetailsVo.getMemberIdx())
                                                                    .build()))
                             .build()
                             .toResponseEntity();
    }

}
