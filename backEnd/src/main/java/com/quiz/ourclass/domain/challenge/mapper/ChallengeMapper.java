package com.quiz.ourclass.domain.challenge.mapper;

import com.quiz.ourclass.domain.challenge.dto.ChallengeRequest;
import com.quiz.ourclass.domain.challenge.entity.Challenge;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ChallengeMapper {

    Challenge challengeRequestToChallenge(ChallengeRequest challengeRequest);
}
