package com.quiz.ourclass.domain.organization.repository;

import com.quiz.ourclass.domain.member.entity.Member;
import com.quiz.ourclass.domain.organization.entity.Organization;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {

    List<Organization> findByManager(Member member);

    Optional<Organization> findByIdAndManager(Long id, Member member);
}
