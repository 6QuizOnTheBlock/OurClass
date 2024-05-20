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

    List<MemberOrganization> findAllByOrganizationOrderByMemberId(Organization organization);

    int countByOrganizationId(long organizationId);

    // 주어진 조직 ID와 멤버 ID 리스트에 속하는 멤버들의 수를 반환하는 메서드
    long countByOrganizationIdAndMemberIdIn(long organizationId, List<Long> memberIds);
}
