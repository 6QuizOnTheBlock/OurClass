package com.quiz.ourclass.domain.chat.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "채팅 필터링 단어 세부 정보 응답 DTO")
public record ChatFilterDTO(
    @Schema(description = "필터링 단어 PK", example = "1")
    Long id,
    @Schema(description = "채팅 메시지 금칙어", example = "어머련아")
    String badWord
) {

}
