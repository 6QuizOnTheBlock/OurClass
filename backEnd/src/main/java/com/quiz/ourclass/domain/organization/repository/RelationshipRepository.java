package com.quiz.ourclass.domain.organization.repository;

import com.quiz.ourclass.domain.organization.entity.Relationship;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RelationshipRepository extends JpaRepository<Relationship, Long> {

    Optional<Relationship> findByOrganizationIdAndMember1IdAndMember2Id(
        long organizationId, long member1Id, long member2Id);
}
