package com.quiz.ourclass.domain.quiz.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;

@Schema(description = "답변 요청")
public record AnswerRequest(
    @Schema(description = "퀴즈게임 아이디")
    long quizGameId,
    @Schema(description = "퀴즈 아이디")
    long quizId,
    @Schema(description = "답변")
    String answer
) implements Serializable {

}
