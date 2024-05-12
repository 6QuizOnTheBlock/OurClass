package com.quiz.ourclass.domain.quiz.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "퀴즈를 위한 첫 회원 정보")
public record QuizStartResponse(
    @Schema(description = "회원 고유번호")
    long id,
    @Schema(description = "퀴즈 게임 고유 번호")
    long quizGameId,
    @Schema(description = "프로필 이미지")
    String photo,
    @Schema(description = "메세지")
    String msg,
    @Schema(description = "엑세스 토큰")
    String accessToken
) {

}
