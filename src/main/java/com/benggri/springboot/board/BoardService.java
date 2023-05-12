package com.benggri.springboot.board;

import com.benggri.springboot.board.vo.BoardVo;
import com.benggri.springboot.comm.vo.CommPaginationResVo;
import com.benggri.springboot.comm.vo.CommResultVo;
import com.benggri.springboot.exception.InternalServerErrorException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardDao boardDao;

    public CommResultVo getBoards(BoardVo boardVo) {
        return CommResultVo.builder()
                           .code(200)
                           .data(CommPaginationResVo.builder()
                                                    .totalItems(boardDao.getBoardsTotCnt(boardVo))
                                                    .data(boardDao.getBoards(boardVo))
                                                    .pageNo(boardVo.getPageNo())
                                                    .limit(boardVo.getLimit())
                                                    .build()
                                                    .pagination())
                           .build();
    }

    public CommResultVo getBoard(BoardVo boardVo) {
        return CommResultVo.builder()
                           .code(200)
                           .data(boardDao.getBoard(boardVo))
                           .build();
    }

    public CommResultVo createBoard(BoardVo boardVo) {
        if (boardDao.createBoard(boardVo) < 1) throw new InternalServerErrorException("게시판 생성 중 오류가 발생하였습니다");

        return CommResultVo.builder().code(200).data(boardVo).build();
    }

    public CommResultVo updateBoard(BoardVo boardVo) {
        if (boardDao.updateBoard(boardVo) < 1) throw new InternalServerErrorException("게시판 수정 중 오류가 발생하였습니다");

        return CommResultVo.builder().code(200).data(boardVo).build();
    }

    public CommResultVo deleteBoard(BoardVo boardVo) {
        if (boardDao.deleteBoard(boardVo) < 1) throw new InternalServerErrorException("게시판 수정 중 오류가 발생하였습니다");

        return CommResultVo.builder().code(200).data(boardVo).build();
    }
}
