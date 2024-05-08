package com.quiz.ourclass.domain.challenge.mapper;

import com.quiz.ourclass.domain.challenge.dto.ChallengeSimpleDTO;
import com.quiz.ourclass.domain.challenge.dto.request.ChallengeRequest;
import com.quiz.ourclass.domain.challenge.dto.response.ChallengeSimpleResponse;
import com.quiz.ourclass.domain.challenge.entity.Challenge;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ChallengeMapper {

    Challenge challengeRequestToChallenge(ChallengeRequest challengeRequest);

    ChallengeSimpleDTO challengeToChallengeSimpleDTO(Challenge challenge);

    ChallengeSimpleResponse challengeToChallengeSimpleResponse(Challenge challenge);
}
