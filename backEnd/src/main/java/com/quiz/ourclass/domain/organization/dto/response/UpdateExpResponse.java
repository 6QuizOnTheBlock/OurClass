package com.quiz.ourclass.domain.organization.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "멤버 포인트 수정 응답 DTO")
public record UpdateExpResponse(
    @Schema(description = "포인트")
    int exp
) {

}
