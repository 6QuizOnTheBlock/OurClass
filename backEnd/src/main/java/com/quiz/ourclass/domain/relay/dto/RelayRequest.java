package com.quiz.ourclass.domain.relay.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "이어달리기 생성 요청 DTO")
public record RelayRequest(
    long organizationId,
    String question
) {

}
