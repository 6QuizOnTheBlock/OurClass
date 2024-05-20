package com.quiz.ourclass.domain.organization.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "멤버 포인트 수정 요청 DTO")
public record UpdateExpRequest(
    @Schema(description = "멤버 id")
    long memberId,
    @Schema(description = "포인트")
    int exp
) {

}
