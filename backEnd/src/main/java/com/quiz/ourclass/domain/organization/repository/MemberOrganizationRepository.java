package com.quiz.ourclass.domain.organization.repository;

import com.quiz.ourclass.domain.organization.entity.MemberOrganization;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberOrganizationRepository extends JpaRepository<MemberOrganization, Long> {

    Optional<MemberOrganization> findByMemberIdAndOrganizationId(Long memberId,
        Long organizationId);
}
