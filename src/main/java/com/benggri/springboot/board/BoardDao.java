package com.benggri.springboot.board;

import com.benggri.springboot.board.vo.BoardVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardDao {

    List<BoardVo> getBoards(BoardVo boardVo);
    int getBoardsTotCnt(BoardVo boardVo);
    BoardVo getBoard(BoardVo boardVo);
    int createBoard(BoardVo boardVo);
    int updateBoard(BoardVo boardVo);
    int deleteBoard(BoardVo boardVo);

}
