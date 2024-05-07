package com.quiz.ourclass.domain.challenge.repository;

import com.quiz.ourclass.domain.challenge.entity.Challenge;
import com.quiz.ourclass.domain.organization.entity.Organization;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChallengeRepository extends JpaRepository<Challenge, Long>,
    ChallengeRepositoryQuerydsl {

    Optional<Challenge> findFirstByOrganizationAndEndStatusIsFalse(Organization organization);

    boolean existsByOrganizationIdAndEndStatusIsFalse(long organizationId);
}
