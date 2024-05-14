package com.quiz.ourclass.domain.member.repository.querydsl;

import static com.quiz.ourclass.domain.member.entity.QMember.member;
import static com.quiz.ourclass.domain.organization.entity.QMemberOrganization.memberOrganization;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.quiz.ourclass.domain.member.dto.response.MemberMeResponse;
import com.quiz.ourclass.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MemberRepositoryQuerydslImpl implements MemberRepositoryQuerydsl {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public MemberMeResponse rememberMe(Member me, long orgId) {

        return jpaQueryFactory.select(Projections.constructor(
                MemberMeResponse.class,
                member.id,
                member.email,
                member.name,
                member.profileImage,
                member.role,
                memberOrganization.exp
            ))
            .from(member)
            .leftJoin(memberOrganization)
            .on(memberOrganization.member.eq(me))
            .where(memberOrganization.id.eq(orgId))
            .fetchOne();
    }
}
