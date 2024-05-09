package com.quiz.ourclass.domain.organization.repository;

import com.quiz.ourclass.domain.organization.entity.Relationship;
import java.util.List;

public interface RelationshipRepositoryQuerydsl {

    List<Relationship> getMemberRelations(long organizationId, long memberId, Long limit);
}
