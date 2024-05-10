package com.quiz.ourclass.domain.chat.dto.request;

import com.quiz.ourclass.global.exception.ErrorCode;
import com.quiz.ourclass.global.exception.GlobalException;

public record ChatFilterSliceRequest(
    Long organizationId,
    Integer page,
    Integer size
) {

    public ChatFilterSliceRequest {
        if (size < 1 || page < 0) {
            throw new GlobalException(ErrorCode.BAD_REQUEST);
        }
    }
}
