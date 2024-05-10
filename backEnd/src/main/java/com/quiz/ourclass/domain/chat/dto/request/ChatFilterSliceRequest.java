package com.quiz.ourclass.domain.chat.dto.request;

import com.quiz.ourclass.global.exception.ErrorCode;
import com.quiz.ourclass.global.exception.GlobalException;
import io.swagger.v3.oas.annotations.media.Schema;

public record ChatFilterSliceRequest(
    @Schema(description = "단체 PK", example = "1")
    Long organizationId,
    @Schema(description = "페이지 (0부터 시작)", example = "0")
    Integer page,
    @Schema(description = "한 페이지 당 사이즈", example = "1")
    Integer size
) {

    public ChatFilterSliceRequest {
        if (size < 1 || page < 0) {
            throw new GlobalException(ErrorCode.BAD_REQUEST);
        }
    }
}
