package com.quiz.ourclass.domain.quiz.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "퀴즈 답변")
public record AnswerResponse(
    @Schema(description = "퀴즈 게임 아이디")
    long quizGameId,
    @Schema(description = "퀴즈 아이디")
    long quizId,
    @Schema(description = "학생 아이디")
    long studentId,
    @Schema(description = "이름")
    String name,
    @Schema(description = "사진")
    String photo,
    @Schema(description = "제출 답")
    String submit,
    @Schema(description = "진짜 답")
    String ans
) {

}
