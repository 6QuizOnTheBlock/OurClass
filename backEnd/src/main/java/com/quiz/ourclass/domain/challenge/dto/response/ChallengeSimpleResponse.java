package com.quiz.ourclass.domain.challenge.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "함께달리기 요약 조회 응답 DTO")
public record ChallengeSimpleResponse(
    @Schema(description = "함께달리기 ID")
    long id,
    @Schema(description = "함께달리기 제목")
    String title,
    @Schema(description = "함께달리기 내용")
    String content,
    @Schema(description = "함께달리기 보상")
    int reward,
    @Schema(description = "함께달리기 시작 시간")
    LocalDateTime startTime,
    @Schema(description = "함께달리기 종료 시간")
    LocalDateTime endTime
) {

}
