package com.quiz.ourclass.domain.member.repository.querydsl;

import com.quiz.ourclass.domain.member.dto.response.MemberMeResponse;
import com.quiz.ourclass.domain.member.entity.Member;
import java.util.List;

public interface MemberRepositoryQuerydsl {

    public MemberMeResponse rememberMe(Member me, long orgId);

    public List<Member> getOrgMembers(long orgId);

}
