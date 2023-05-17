package com.benggri.springboot.post;

import com.benggri.springboot.post.vo.PostInfoVo;
import com.benggri.springboot.post.vo.PostVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostDao {

    List<PostVo> getPosts(PostVo postVo);
    int getPostsTotCnt(PostVo postVo);
    PostVo getPost(PostVo postVo);
    int createPost(PostVo postVo);
    int createPostInfo(PostVo postVo);
    int updatePost(PostVo postVo);
    int deletePost(PostVo postVo);
    int deletePostInfo(PostVo postVo);
    int updatePostView(PostVo postVo);
    int updatePostLike(PostVo postVo);
    int updatePostLikeCancel(PostVo postVo);
    int updatePostUnlike(PostVo postVo);
    int updatePostUnlikeCancel(PostVo postVo);
    PostInfoVo getPostLike(PostInfoVo postInfoVo);
    int createPostLike(PostInfoVo postInfoVo);
    int deletePostLike(PostInfoVo postInfoVo);
    PostInfoVo getPostUnlike(PostInfoVo postInfoVo);
    int createPostUnlike(PostInfoVo postInfoVo);
    int deletePostUnlike(PostInfoVo postInfoVo);
}
