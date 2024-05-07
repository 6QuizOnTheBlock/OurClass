package com.quiz.ourclass.domain.relay.repository;

import static com.quiz.ourclass.domain.relay.entity.QRelay.relay;
import static com.quiz.ourclass.domain.relay.entity.QRelayMember.relayMember;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.quiz.ourclass.domain.relay.dto.request.RelaySliceRequest;
import com.quiz.ourclass.domain.relay.dto.response.RelaySimpleResponse;
import com.quiz.ourclass.domain.relay.dto.response.RelaySliceResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class RelayRepositoryQuerydslImpl implements RelayRepositoryQuerydsl {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public RelaySliceResponse getRelays(RelaySliceRequest request) {
        Pageable pageable = PageRequest.of(request.page(), request.size());
        BooleanBuilder relayCondition = getRelaysBooleanBuilder(request);

        List<RelaySimpleResponse> relays = jpaQueryFactory.select(Projections.constructor(
                RelaySimpleResponse.class,
                relay.id,
                relay.startRunner.receiveTime,
                relay.lastRunner.receiveTime,
                relay.lastRunner.turn,
                relay.lastRunner.curMember.name
            ))
            .from(relay)
            .join(relay.relayRunners, relayMember)
            .where(relayCondition)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize() + 1)
            .orderBy(relay.id.desc())
            .fetch();

        boolean hasNext = false;
        if (relays.size() > pageable.getPageSize()) {
            relays.remove(pageable.getPageSize());
            hasNext = true;
        }
        return RelaySliceResponse.builder().relays(relays).page(request.page()).size(request.size())
            .last(hasNext).build();
    }

    private static BooleanBuilder getRelaysBooleanBuilder(RelaySliceRequest request) {
        BooleanBuilder relayCondition = new BooleanBuilder();
        if (request.orgId() != null && request.orgId() > 0) {
            relayCondition.and(relay.organization.id.eq(request.orgId()));
        }
        if (request.memberId() != null && request.memberId() > 0) {
            JPQLQuery<Long> subQuery = JPAExpressions
                .select(relayMember.relay.id)
                .from(relayMember)
                .join(relayMember.relay, relay)
                .where(relayMember.curMember.id.eq(request.memberId()));
            relayCondition.and(relay.id.in(subQuery));
        }
        return relayCondition;
    }
}
