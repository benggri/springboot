package com.benggri.springboot.comm.test;

import com.benggri.springboot.comm.test.vo.CommTestVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommTestDao {

    List<CommTestVo> getTests(CommTestVo commTestVo);
    int getTestsTotCnt(CommTestVo commTestVo);
    CommTestVo getTest(CommTestVo commTestVo);
    int createTest(CommTestVo commTestVo);
    int updateTest(CommTestVo commTestVo);
    int deleteTest(CommTestVo commTestVo);

}
