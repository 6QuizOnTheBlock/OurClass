package com.quiz.ourclass.domain.organization.repository;

import com.quiz.ourclass.domain.member.entity.Member;
import com.quiz.ourclass.domain.organization.entity.Organization;
import com.quiz.ourclass.domain.organization.entity.Relationship;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RelationshipRepository extends JpaRepository<Relationship, Long>,
    RelationshipRepositoryQuerydsl {

    Optional<Relationship> findByOrganizationIdAndMember1IdAndMember2Id(
        long organizationId, long member1Id, long member2Id);

    List<Relationship> findAllByOrganization(Organization organization);

    List<Relationship> findAllByOrganizationIdOrderByMember1(Long orgId);

    List<Relationship> findByMember1(Member member);

    @Query("SELECT r FROM Relationship r "
        + "WHERE r.organization.id = :organizationId "
        + "AND r.member1.id IN :memberIds "
        + "AND r.member2.id IN :memberIds"
    )
    List<Relationship> findAllByOrganizationIdAndMemberIds(
        @Param("organizationId") long organizationId,
        @Param("memberIds") List<Long> memberIds
    );

}
