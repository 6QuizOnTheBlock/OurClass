package com.quiz.ourclass.domain.organization.repository;

import com.quiz.ourclass.domain.member.entity.Member;
import com.quiz.ourclass.domain.organization.entity.MemberOrganization;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberOrganizationRepository extends JpaRepository<MemberOrganization, Long> {

    List<MemberOrganization> findByMember(Member member);

    Optional<MemberOrganization> findByMemberIdAndOrganizationId(Long memberId,
        Long organizationId);

    List<MemberOrganization> findByOrganizationIdOrderByExpDesc(long organizationId);
}
