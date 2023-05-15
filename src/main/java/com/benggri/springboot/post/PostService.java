package com.benggri.springboot.post;


import com.benggri.springboot.comm.vo.CommPaginationResVo;
import com.benggri.springboot.comm.vo.CommResultVo;
import com.benggri.springboot.exception.BadRequestException;
import com.benggri.springboot.exception.InternalServerErrorException;
import com.benggri.springboot.exception.UnauthorizedException;
import com.benggri.springboot.post.vo.PostInfoVo;
import com.benggri.springboot.post.vo.PostVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.benggri.springboot.comm.util.DateUtil.getFullTime;
import static com.benggri.springboot.comm.util.StringUtil.isEmptyObj;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostDao postDao;

    public CommResultVo getPosts(PostVo postVo) {
        return CommResultVo.builder()
                           .code(200)
                           .data(CommPaginationResVo.builder()
                                                    .totalItems(postDao.getPostsTotCnt(postVo))
                                                    .data(postDao.getPosts(postVo))
                                                    .pageNo(postVo.getPageNo())
                                                    .limit(postVo.getLimit())
                                                    .build()
                                                    .pagination())
                           .build();

    }

    public CommResultVo getPost(PostVo postVo) {
        PostVo post = postDao.getPost(postVo);
        if (isEmptyObj(post)) throw new BadRequestException("존재하지 않는 게시글입니다");

        if (post.getWriterIdx() != postVo.getWriterIdx()) {
            postDao.updatePostView(postVo); // 조회수 증가
        }

        return CommResultVo.builder().code(200).data(post).build();
    }

    @Transactional
    public CommResultVo createPost(PostVo postVo) {
        postVo.setWriteDate(getFullTime());
        if (postDao.createPost(postVo) < 1) throw new InternalServerErrorException("게시글 등록 중 오류가 발생하였습니다");
        if (postDao.createPostInfo(postVo) < 1) throw new InternalServerErrorException("게시글 등록 중 오류가 발생하였습니다");

        return CommResultVo.builder().code(200).data(postVo).build();
    }

    @Transactional
    public CommResultVo updatePost(PostVo postVo) {
        PostVo post = postDao.getPost(postVo);
        if (isEmptyObj(post)) throw new BadRequestException("존재하지 않는 게시글입니다");

        if (post.getWriterIdx() != postVo.getWriterIdx()) throw new UnauthorizedException("게시글 수정은 작성자만 할 수 있습니다");
        if (postDao.updatePost(postVo) < 1) throw new InternalServerErrorException("게시글 수정 중 오류가 발생하였습니다");

        return CommResultVo.builder().code(200).data(postVo).build();
    }

    @Transactional
    public CommResultVo deletePost(PostVo postVo) {
        PostVo post = postDao.getPost(postVo);
        if (isEmptyObj(post)) throw new BadRequestException("존재하지 않는 게시글입니다");

        if (post.getWriterIdx() != postVo.getWriterIdx()) throw new UnauthorizedException("게시글 삭제는 작성자만 할 수 있습니다");
        if (postDao.deletePost(postVo) < 1) throw new InternalServerErrorException("게시글 삭제 중 오류가 발생하였습니다");

        return CommResultVo.builder().code(200).data("게시글 삭제가 완료되었습니다").build();
    }

    @Transactional
    public CommResultVo updatePostLike(PostVo postVo) {
        PostVo post = postDao.getPost(postVo);
        if (isEmptyObj(post)) throw new BadRequestException("존재하지 않는 게시글입니다");

        PostInfoVo unlike = postDao.getPostUnlike(postVo.toPostInfoVo());
        if (!isEmptyObj(unlike)) {
            postDao.deletePostUnlike(unlike);
        }

        PostInfoVo like   = postDao.getPostLike(postVo.toPostInfoVo());
        if (isEmptyObj(like)) {
            postDao.updatePostLike(postVo);
            postDao.createPostLike(postVo.toPostInfoVo());
        } else {
            throw new BadRequestException("이미 좋아요 하셨습니다");
        }

        return CommResultVo.builder().code(200).data(postVo).build();
    }

    @Transactional
    public CommResultVo updatePostLikeCancel(PostVo postVo) {
        PostVo post = postDao.getPost(postVo);
        if (isEmptyObj(post)) throw new BadRequestException("존재하지 않는 게시글입니다");

        PostInfoVo like   = postDao.getPostLike(postVo.toPostInfoVo());
        if (isEmptyObj(like)) {
            throw new BadRequestException("좋아요 한 적이 없는 게시글입니다");
        } else {
            postDao.updatePostLikeCancel(postVo);
            postDao.deletePostLike(postVo.toPostInfoVo());
        }

        return CommResultVo.builder().code(200).data(postVo).build();
    }

    @Transactional
    public CommResultVo updatePostUnlike(PostVo postVo) {
        PostVo post = postDao.getPost(postVo);
        if (isEmptyObj(post)) throw new BadRequestException("존재하지 않는 게시글입니다");

        PostInfoVo like   = postDao.getPostLike(postVo.toPostInfoVo());
        if (!isEmptyObj(like)) {
            postDao.deletePostLike(like);
        }

        PostInfoVo unlike = postDao.getPostUnlike(postVo.toPostInfoVo());
        if (isEmptyObj(unlike)) {
            postDao.updatePostUnlike(postVo);
            postDao.createPostUnlike(postVo.toPostInfoVo());
        } else {
            throw new BadRequestException("이미 싫어요 하셨습니다");
        }

        return CommResultVo.builder().code(200).data(postVo).build();
    }

    @Transactional
    public CommResultVo updatePostUnlikeCancel(PostVo postVo) {
        PostVo post = postDao.getPost(postVo);
        if (isEmptyObj(post)) throw new BadRequestException("존재하지 않는 게시글입니다");

        PostInfoVo unlike = postDao.getPostUnlike(postVo.toPostInfoVo());
        if (isEmptyObj(unlike)) {
            throw new BadRequestException("싫어요 한 적이 없는 게시글입니다");
        } else {
            postDao.updatePostUnlikeCancel(postVo);
            postDao.deletePostUnlike(postVo.toPostInfoVo());
        }

        return CommResultVo.builder().code(200).data(postVo).build();
    }

}
