package com.quiz.ourclass.domain.quiz.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;

@Schema(description = "현재 유저들이 풀어야할 퀴즈를 전송")
public record QuestionRequest(
    @Schema(description = "퀴즈 묶음의 번호")
    int quizGameId,
    @Schema(description = "묶음 속 현재 풀 퀴즈의 번호")
    int id
) implements Serializable {

}
