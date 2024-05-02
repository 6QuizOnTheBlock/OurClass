package com.quiz.ourclass.domain.challenge.dto.response;

import com.quiz.ourclass.domain.challenge.dto.ChallengeSimpleDTO;
import java.util.List;
import lombok.Builder;

@Builder
public record ChallengeResponse(
    ChallengeSimpleDTO challengeSimpleDTO,
    List<ReportResponse> reports
) {

}
