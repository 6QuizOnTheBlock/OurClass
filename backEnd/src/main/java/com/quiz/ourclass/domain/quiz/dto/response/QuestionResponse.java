package com.quiz.ourclass.domain.quiz.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "응답")
public record QuestionResponse(
    long id,
    String question,
    String answer,
    long point,
    String candidate1,
    String candidate2,
    String candidate3,
    String candidate4

) {

}
