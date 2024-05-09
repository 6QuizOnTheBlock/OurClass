package com.quiz.ourclass.domain.chat.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "채팅 메시지 금칙어 요청 DTO")
public record ChatFilterRequest(
    @Schema(description = "채팅 메시지 금칙어", example = "어머련아")
    String word
) {

}
