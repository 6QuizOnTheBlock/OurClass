package com.quiz.ourclass.domain.challenge.repository;

import com.quiz.ourclass.domain.challenge.entity.Challenge;
import com.quiz.ourclass.domain.challenge.entity.ChallengeGroup;
import com.quiz.ourclass.domain.member.entity.Member;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChallengeGroupRepository extends JpaRepository<ChallengeGroup, Long> {

    List<ChallengeGroup> findByChallengeAndCompleteStatusIsFalse(Challenge challenge);

    Optional<ChallengeGroup> findDistinctChallengeGroupByChallengeAndGroupMembersMember(
        Challenge challenge, Member member);
}
