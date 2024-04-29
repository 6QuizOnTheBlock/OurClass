package com.quiz.ourclass.domain.challenge.repository;

import static com.quiz.ourclass.domain.challenge.entity.QChallenge.challenge;
import static com.quiz.ourclass.domain.challenge.entity.QChallengeGroup.challengeGroup;
import static com.quiz.ourclass.domain.challenge.entity.QGroupMember.groupMember;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.quiz.ourclass.domain.challenge.dto.ChallengSliceRequest;
import com.quiz.ourclass.domain.challenge.dto.ChallengeSimpleDTO;
import com.quiz.ourclass.domain.challenge.dto.ChallengeSliceResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class ChallengeRepositoryQuerydslImpl implements ChallengeRepositoryQuerydsl {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public ChallengeSliceResponse getChallenges(ChallengSliceRequest request) {
        Pageable pageable = PageRequest.of(request.page(), request.size());
        BooleanBuilder booleanBuilder = getChallengesBooleanBuilder(request);

        List<ChallengeSimpleDTO> challenges = jpaQueryFactory.select(Projections.fields(
                ChallengeSimpleDTO.class,
                challenge.id,
                challenge.title,
                challenge.content,
                challenge.headCount,
                challenge.startTime,
                challenge.endTime
            ))
            .from(challenge)
            .where(booleanBuilder)
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
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (request.orgId() != null && request.orgId() > 0) {
            booleanBuilder.and(challenge.organization.id.eq(request.orgId()));
        }
        if (request.memberId() != null && request.memberId() > 0) {
            JPQLQuery<Long> subQuery = JPAExpressions
                .select(challengeGroup.challenge.id)
                .from(groupMember)
                .join(groupMember.challengeGroup, challengeGroup)
                .where(groupMember.member.id.eq(request.memberId()));
            booleanBuilder.and(challenge.id.in(subQuery));
        }
        return booleanBuilder;
    }
}
