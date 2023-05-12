package com.benggri.springboot.post;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@Tag(name="post-controller", description="게시글 관련 API")
@RestController
@RequiredArgsConstructor
public class PostController {

    private PostService postService;



}
