package com.quiz.ourclass.domain.organization.repository;

import static com.quiz.ourclass.domain.organization.entity.QRelationship.relationship;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.quiz.ourclass.domain.organization.entity.Relationship;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RelationshipRepositoryQuerydslImpl implements RelationshipRepositoryQuerydsl {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Relationship> getMemberRelations(long organizationId, long memberId,
        Long limit) {
        JPAQuery<Relationship> query = jpaQueryFactory.select(relationship)
            .from(relationship)
            .where(relationship.organization.id.eq(organizationId))
            .where(relationship.member1.id.eq(memberId).or(relationship.member2.id.eq(memberId)));
        if (limit != null && limit > 0) {
            query.limit(limit);
        }
        return query.orderBy(relationship.relationPoint.desc()).fetch();
    }
}
