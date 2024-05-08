package com.quiz.ourclass.domain.organization.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "학급 정보 수정 응답 DTO")
public record UpdateOrganizationResponse(
    @Schema(description = "학급 이름")
    String name
) {

}
