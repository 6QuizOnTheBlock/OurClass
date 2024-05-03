package com.quiz.ourclass.domain.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record UpdateFcmTokenRequest(
    @Schema(description = "FCM 토큰", example = "zxcvoiuwaerlknsdlkfnklasdf/111asjndkl123dasd")
    String fcmToken
) {

}
