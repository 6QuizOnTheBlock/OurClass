package com.quiz.ourclass.domain.organization.repository;

import com.quiz.ourclass.domain.member.entity.Member;
import com.quiz.ourclass.domain.organization.entity.MemberOrganization;
import com.quiz.ourclass.domain.organization.entity.Organization;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberOrganizationRepository extends JpaRepository<MemberOrganization, Long>,
    MemberOrganizationRepositoryQuerydsl {

    List<MemberOrganization> findByMember(Member member);

    List<MemberOrganization> findByOrganization(Organization organization);

    Optional<MemberOrganization> findByMemberIdAndOrganizationId(Long memberId,
        Long organizationId);

    List<MemberOrganization> findByOrganizationIdOrderByExpDesc(long organizationId);

    Optional<MemberOrganization> findByOrganizationAndMember(Organization organization,
        Member member);
}
