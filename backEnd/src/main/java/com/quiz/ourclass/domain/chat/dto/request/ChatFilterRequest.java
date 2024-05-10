package com.quiz.ourclass.domain.chat.dto.request;

import com.quiz.ourclass.global.exception.ErrorCode;
import com.quiz.ourclass.global.exception.GlobalException;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "채팅 메시지 금칙어 요청 DTO")
public record ChatFilterRequest(
    @Schema(description = "채팅 메시지 금칙어", example = "어머련아")
    String badWord
) {

    public ChatFilterRequest {
        if (badWord.replaceAll("\\s+", "").isEmpty()) {
            throw new GlobalException(ErrorCode.BAD_REQUEST);
        }
    }
}
