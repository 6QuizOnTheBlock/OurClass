package com.quiz.ourclass.domain.challenge.repository;

import com.quiz.ourclass.domain.challenge.entity.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChallengeRepository extends JpaRepository<Challenge, Long>,
    ChallengeRepositoryQuerydsl {

}
