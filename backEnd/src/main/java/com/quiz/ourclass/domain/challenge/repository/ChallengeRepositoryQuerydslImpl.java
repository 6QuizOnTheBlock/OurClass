package com.quiz.ourclass.domain.challenge.repository;

import static com.quiz.ourclass.domain.challenge.entity.QChallenge.challenge;
import static com.quiz.ourclass.domain.challenge.entity.QChallengeGroup.challengeGroup;
import static com.quiz.ourclass.domain.challenge.entity.QGroupMember.groupMember;
import static com.quiz.ourclass.domain.challenge.entity.QReport.report;
import static com.quiz.ourclass.domain.member.entity.QMember.member;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.quiz.ourclass.domain.challenge.dto.ChallengeSimpleDTO;
import com.quiz.ourclass.domain.challenge.dto.request.ChallengSliceRequest;
import com.quiz.ourclass.domain.challenge.dto.response.ChallengeResponse;
import com.quiz.ourclass.domain.challenge.dto.response.ChallengeSliceResponse;
import com.quiz.ourclass.domain.challenge.dto.response.GroupMatchingResponse;
import com.quiz.ourclass.domain.challenge.dto.response.ReportResponse;
import com.quiz.ourclass.global.dto.MemberSimpleDTO;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
@Log4j2
public class ChallengeRepositoryQuerydslImpl implements ChallengeRepositoryQuerydsl {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public ChallengeSliceResponse getChallenges(ChallengSliceRequest request) {
        Pageable pageable = PageRequest.of(request.page(), request.size());
        BooleanBuilder challengeCondition = getChallengesBooleanBuilder(request);

        List<ChallengeSimpleDTO> challenges = jpaQueryFactory.select(Projections.constructor(
                ChallengeSimpleDTO.class,
                challenge.id,
                challenge.title,
                challenge.content,
                challenge.headCount,
                challenge.startTime,
                challenge.endTime
            ))
            .from(challenge)
            .where(challengeCondition)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize() + 1)
            .orderBy(challenge.id.desc())
            .fetch();

        boolean hasNext = false;
        if (challenges.size() > pageable.getPageSize()) {
            challenges.remove(pageable.getPageSize());
            hasNext = true;
        }
        return ChallengeSliceResponse.builder().challenges(challenges).page(request.page()).size(
                request.size())
            .last(hasNext).build();
    }

    private static BooleanBuilder getChallengesBooleanBuilder(ChallengSliceRequest request) {
        BooleanBuilder challengeCondition = new BooleanBuilder();
        if (request.orgId() != null && request.orgId() > 0) {
            challengeCondition.and(challenge.organization.id.eq(request.orgId()));
        }
        if (request.memberId() != null && request.memberId() > 0) {
            JPQLQuery<Long> subQuery = JPAExpressions
                .select(challengeGroup.challenge.id)
                .from(groupMember)
                .join(groupMember.challengeGroup, challengeGroup)
                .where(groupMember.member.id.eq(request.memberId()));
            challengeCondition.and(challenge.id.in(subQuery));
        }
        return challengeCondition;
    }

    @Override
    public ChallengeResponse getChallengeDetail(long id, Long groupId) {
        ChallengeSimpleDTO challengeSimpleDTO = jpaQueryFactory
            .select(Projections.constructor(
                ChallengeSimpleDTO.class,
                challenge.id,
                challenge.title,
                challenge.content,
                challenge.headCount,
                challenge.startTime,
                challenge.endTime))
            .from(challenge)
            .where(challenge.id.eq(id))
            .fetchOne();

        BooleanBuilder groupCondition = new BooleanBuilder();
        if (groupId != null && groupId > 0) {
            groupCondition.and(challengeGroup.id.eq(groupId));
        }

        List<ReportResponse> reportResponses = jpaQueryFactory
            .select(Projections.fields(
                ReportResponse.class,
                report.id.as("id"),
                Projections.constructor(
                    GroupMatchingResponse.class,
                    challengeGroup.id,
                    challengeGroup.headCount,
                    challengeGroup.leaderId,
                    Expressions.constant(new ArrayList<MemberSimpleDTO>())
                ).as("group"),
                challengeGroup.createTime.as("startTime"),
                report.createTime.as("endTime"),
                report.file.as("file"),
                report.content.as("content"),
                report.acceptStatus.as("acceptStatus")
            ))
            .from(report)
            .join(report.challengeGroup, challengeGroup)
            .on(challengeGroup.challenge.id.eq(id))
            .where(groupCondition)
            .fetch();

        for (ReportResponse reportResponse : reportResponses) {
            List<MemberSimpleDTO> members = jpaQueryFactory
                .select(Projections.constructor(
                    MemberSimpleDTO.class,
                    member.id,
                    member.name,
                    member.profileImage.as("photo")
                ))
                .from(groupMember)
                .join(groupMember.member, member)
                .where(groupMember.challengeGroup.id.eq(reportResponse.getGroup().getId()))
                .fetch();
            reportResponse.getGroup().setStudents(members);
        }

        return ChallengeResponse.builder().challengeSimpleDTO(challengeSimpleDTO)
            .reports(reportResponses)
            .build();
    }
}
