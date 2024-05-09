package com.quiz.ourclass.domain.chat.dto.request;

import com.quiz.ourclass.global.exception.ErrorCode;
import com.quiz.ourclass.global.exception.GlobalException;

public record MessageSliceRequest(
    Long roomId,
    Integer page,
    Integer size
) {

    public MessageSliceRequest {
        if (size < 1 || page < 0) {
            throw new GlobalException(ErrorCode.BAD_REQUEST);
        }
    }
}
