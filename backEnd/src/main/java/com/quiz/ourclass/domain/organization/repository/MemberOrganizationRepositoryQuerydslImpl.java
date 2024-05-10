package com.quiz.ourclass.domain.organization.repository;

import static com.quiz.ourclass.domain.organization.entity.QMemberOrganization.memberOrganization;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.quiz.ourclass.domain.organization.dto.response.MemberPlayCountResponse;
import com.quiz.ourclass.global.dto.MemberSimpleDTO;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MemberOrganizationRepositoryQuerydslImpl implements
    MemberOrganizationRepositoryQuerydsl {

    private final JPAQueryFactory jpaQueryFactory;

    public List<MemberPlayCountResponse> getChallengeCountByOrganizationId(Long id) {
        return jpaQueryFactory.select(Projections.constructor(
                MemberPlayCountResponse.class,
                Projections.constructor(MemberSimpleDTO.class,
                    memberOrganization.member.id,
                    memberOrganization.member.name,
                    memberOrganization.member.profileImage),
                memberOrganization.challengeCount
            ))
            .from(memberOrganization)
            .where(memberOrganization.organization.id.eq(id))
            .orderBy(memberOrganization.challengeCount.desc())
            .fetch();
    }

    @Override
    public List<MemberPlayCountResponse> getRelayCountByOrganizationId(Long id) {
        return jpaQueryFactory.select(Projections.constructor(
                MemberPlayCountResponse.class,
                Projections.constructor(MemberSimpleDTO.class,
                    memberOrganization.member.id,
                    memberOrganization.member.name,
                    memberOrganization.member.profileImage),
                memberOrganization.relayCount
            ))
            .from(memberOrganization)
            .where(memberOrganization.organization.id.eq(id))
            .orderBy(memberOrganization.relayCount.desc())
            .fetch();
    }
}
