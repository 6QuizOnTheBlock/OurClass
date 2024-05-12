package com.quiz.ourclass.domain.quiz.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "퀴즈를 시작하려는 사람 검증")
public record QuizStartRequest(
    @Schema(description = "퀴즈를 시작하겠다는 사람의 uri")
    String email,
    @Schema(description = "퀴즈 방의 UUID(퀴즈가 시작 되었을 때만 유효한 랜덤 값)")
    String uuid) {

}
