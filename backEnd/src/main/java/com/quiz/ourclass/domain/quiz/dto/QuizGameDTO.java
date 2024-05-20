package com.quiz.ourclass.domain.quiz.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = " 퀴즈 묶음에 대한 명세")
public record QuizGameDTO(
    @Schema(description = "퀴즈 고유 번호")
    long id,
    @Schema(description = "해당 퀴즈가 속한 단체의 고유 번호")
    long orgId,
    @Schema(description = "퀴즈 개수")
    int quizCount,
    @Schema(description = "제한 시간")
    long limitTime
) {

}
