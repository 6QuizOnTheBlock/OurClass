package com.quiz.ourclass.domain.challenge.dto.response;

import com.quiz.ourclass.domain.challenge.dto.ChallengeSimpleDTO;
import lombok.Builder;

@Builder
public record RunningChallengeResponse(
    ChallengeSimpleDTO challengeSimpleDTO,
    int waitingCount,
    int doneMemberCount
) {

}
