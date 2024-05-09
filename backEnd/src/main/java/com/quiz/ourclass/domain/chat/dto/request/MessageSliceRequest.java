package com.quiz.ourclass.domain.chat.dto.request;

import com.quiz.ourclass.global.exception.ErrorCode;
import com.quiz.ourclass.global.exception.GlobalException;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "채팅 메시지 내역 조회 요청 DTO")
public record MessageSliceRequest(
    @Schema(description = "채팅방 ID", example = "1")
    Long roomId,
    @Schema(description = "페이지 (0부터 시작)", example = "0")
    Integer page,
    @Schema(description = "한 페이지 당 사이즈", example = "1")
    Integer size
) {

    public MessageSliceRequest {
        if (size < 1 || page < 0) {
            throw new GlobalException(ErrorCode.BAD_REQUEST);
        }
    }
}
