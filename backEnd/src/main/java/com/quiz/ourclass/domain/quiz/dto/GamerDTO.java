package com.quiz.ourclass.domain.quiz.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;

@Schema(description = "퀴즈 전용 [Socket]에서 왔다갔다 할 DTO")
public record GamerDTO(
    @Schema(description = "회원 고유번호")
    long id,
    @Schema(description = "퀴즈 게임 고유 번호")
    long quizGameId,
    @Schema(description = "프로필 이미지")
    String photo,
    @Schema(description = "이름")
    String name,
    @Schema(description = "점수")
    int point
) implements Serializable {


}
