package com.benggri.springboot.member;

import com.benggri.springboot.comm.vo.CommPaginationResVo;
import com.benggri.springboot.comm.vo.CommResultVo;
import com.benggri.springboot.exception.BadRequestException;
import com.benggri.springboot.member.vo.MemberVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.benggri.springboot.comm.util.StringUtil.isEmptyObj;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberDao memberDao;

    public CommResultVo getMembers(MemberVo memberVo) {
        return CommResultVo.builder()
                           .code(200)
                           .data(CommPaginationResVo.builder()
                                                    .totalItems(memberDao.getMembersTotCnt(memberVo))
                                                    .data(memberDao.getMembers(memberVo))
                                                    .pageNo(memberVo.getPageNo())
                                                    .limit(memberVo.getLimit())
                                                    .build()
                                                    .pagination())
                           .build();
    }

    public CommResultVo getMember(MemberVo memberVo) {
        MemberVo result = memberDao.getMember(memberVo);
        if ( isEmptyObj(result) ) throw new BadRequestException("존재하지 않는 회원입니다");

        result = getMemberInfoEtc(result);
        return CommResultVo.builder().code(200).data(result).build();
    }

    public CommResultVo getMemberByMemberId(MemberVo memberVo) {
        MemberVo result = memberDao.getMemberByMemberId(memberVo);
        if ( isEmptyObj(result) ) throw new BadRequestException("존재하지 않는 회원입니다");

        result = getMemberInfoEtc(result);
        return CommResultVo.builder().code(200).data(result).build();
    }

    @Transactional
    public int createMember(MemberVo memberVo) {
        memberDao.createMember(memberVo);
        return 1;
    }

    public CommResultVo updateMember(MemberVo memberVo) {
        memberDao.updateMember(memberVo);
        return CommResultVo.builder().code(200).msg("수정완료되었습니다").build();
    }

    public int deleteMember(MemberVo memberVo) {
        return memberDao.deleteMember(memberVo);
    }

    /**
     * 회원 ID 기반 가입 여부 확인
     * @param memberVo
     * @return
     */
    public MemberVo checkMember(MemberVo memberVo) {
        return memberDao.getMemberByMemberId(memberVo);
    }

    private MemberVo getMemberInfoEtc(MemberVo memberVo) {
        return memberVo;
    }

}
