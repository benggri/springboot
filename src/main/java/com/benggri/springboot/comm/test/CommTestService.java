package com.benggri.springboot.comm.test;

import com.benggri.springboot.comm.test.vo.CommTestVo;
import com.benggri.springboot.comm.vo.CommPaginationResVo;
import com.benggri.springboot.comm.vo.CommResultVo;
import com.benggri.springboot.exception.BadRequestException;
import com.benggri.springboot.exception.InternalServerErrorException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.benggri.springboot.comm.util.StringUtil.isEmptyObj;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommTestService {

    private final CommTestDao commTestDao;

    public CommResultVo getTests(CommTestVo commTestVo) {
        return CommResultVo.builder()
                           .code(200)
                           .data(CommPaginationResVo.builder()
                                                    .totalItems(commTestDao.getTestsTotCnt(commTestVo))
                                                    .data(commTestDao.getTests(commTestVo))
                                                    .pageNo(commTestVo.getPageNo())
                                                    .limit(commTestVo.getLimit())
                                                    .build()
                                                    .pagination())
                           .build();
    }

    public CommResultVo getTest(CommTestVo commTestVo) {
        CommTestVo result = commTestDao.getTest(commTestVo);
        if ( isEmptyObj(result) ) throw new BadRequestException("존재하지 않는 게시글입니다");

        return CommResultVo.builder().code(200).data(result).build();
    }

    public CommResultVo createTest(CommTestVo commTestVo) {
        if ( commTestDao.createTest(commTestVo) < 1 ) throw new InternalServerErrorException("게시글 등록에 실패했습니다");

        return CommResultVo.builder()
                           .code(200)
                           .msg("게시글 등록되었습니다")
                           .build();
    }

    public CommResultVo updateTest(CommTestVo commTestVo) {
        CommTestVo result = commTestDao.getTest(commTestVo);
        if ( isEmptyObj(result) ) throw new BadRequestException("존재하지 않는 게시글입니다");
        if ( commTestDao.updateTest(commTestVo) < 1 ) throw new InternalServerErrorException("게시글 수정에 실패했습니다");

        return CommResultVo.builder()
                           .code(200)
                           .msg("게시글 수정되었습니다")
                           .build();
    }

    public CommResultVo deleteTest(CommTestVo commTestVo) {
        CommTestVo result = commTestDao.getTest(commTestVo);
        if ( isEmptyObj(result) ) throw new BadRequestException("존재하지 않는 게시글입니다");
        if ( commTestDao.deleteTest(commTestVo) < 1 ) throw new InternalServerErrorException("게시글 삭제에 실패했습니다");

        return CommResultVo.builder()
                           .code(200)
                           .msg("게시글 삭제되었습니다")
                           .build();
    }

}
