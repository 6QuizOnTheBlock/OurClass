package com.quiz.ourclass.domain.quiz.dto;

import com.quiz.ourclass.domain.quiz.entity.Qtype;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "하나의 문제에 대한 명세")
public record QuizDTO(
    @Schema(description = "질문")
    String question,
    @Schema(description = "번호 ①")
    String candidate1,
    @Schema(description = "번호 ②")
    String candidate2,
    @Schema(description = "번호 ③")
    String candidate3,
    @Schema(description = "번호 ④")
    String candidate4,
    @Schema(description = "문제 타입")
    Qtype qtype,
    @Schema(description = "문제 맞출 시 포인트")
    long point,
    @Schema(description = "질문에 대한 답 - 주관식은 그에 대한 답이 적혀있고, 객관식은 번호")
    String answer
) {

}
