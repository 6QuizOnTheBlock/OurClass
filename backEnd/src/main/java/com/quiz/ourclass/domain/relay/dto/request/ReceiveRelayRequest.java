package com.quiz.ourclass.domain.relay.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "이어달리기 받기 요청 DTO")
public record ReceiveRelayRequest(
    @Schema(description = "이어달리기 전달자 id")
    long senderId,
    @Schema(description = "이어달리기 전달 질문")
    String question
) {

}
