package com.quiz.ourclass.domain.challenge.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "함께달리기 목록 조회 요청 DTO")
public record ChallengeSliceRequest(
    @Schema(description = "학급 id")
    Long orgId,
    @Schema(description = "멤버 id")
    Long memberId,
    @Schema(description = "페이지 (0부터 시작)")
    Integer page,
    @Schema(description = "한 페이지 당 사이즈")
    Integer size
) {

}
