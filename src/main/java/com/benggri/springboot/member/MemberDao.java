package com.benggri.springboot.member;

import com.benggri.springboot.member.vo.MemberVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberDao {

    List<MemberVo> getMembers(MemberVo memberVo);
    int getMembersTotCnt(MemberVo memberVo);
    MemberVo getMember(MemberVo memberVo);
    MemberVo getMemberByMemberId(MemberVo memberVo);
    int createMember(MemberVo memberVo);
    int updateMember(MemberVo memberVo);
    int deleteMember(MemberVo memberVo);

}
