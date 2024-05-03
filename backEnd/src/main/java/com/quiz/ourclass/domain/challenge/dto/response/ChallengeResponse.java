package com.quiz.ourclass.domain.challenge.dto.response;

import com.quiz.ourclass.domain.challenge.dto.ChallengeSimpleDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;

@Builder
@Schema(description = "함께달리기 상세 조회 응답 DTO")
public record ChallengeResponse(
    ChallengeSimpleDTO challengeSimpleDTO,
    @Schema(description = "관련 레포드 목록")
    List<ReportResponse> reports
) {

}
