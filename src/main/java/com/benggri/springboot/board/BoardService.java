package com.benggri.springboot.board;

import com.benggri.springboot.board.vo.BoardVo;
import com.benggri.springboot.comm.vo.CommPaginationResVo;
import com.benggri.springboot.comm.vo.CommResultVo;
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
        int result = boardDao.createBoard(boardVo);
        if (result < 1) return CommResultVo.builder().code(500).build();

        return CommResultVo.builder().code(200).data(boardVo).build();
    }

    public CommResultVo updateBoard(BoardVo boardVo) {
        int result = boardDao.updateBoard(boardVo);
        if (result < 1) return CommResultVo.builder().code(400).build();

        return CommResultVo.builder().code(200).data(boardVo).build();
    }

    public CommResultVo deleteBoard(BoardVo boardVo) {
        int result = boardDao.deleteBoard(boardVo);
        if (result < 1) return CommResultVo.builder().code(400).build();

        return CommResultVo.builder().code(200).data(boardVo).build();
    }
}
