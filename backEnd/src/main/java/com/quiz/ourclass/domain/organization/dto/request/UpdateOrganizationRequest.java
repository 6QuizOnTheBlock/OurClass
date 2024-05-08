package com.quiz.ourclass.domain.organization.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "학급 정보 수정 요청 DTO")
public record UpdateOrganizationRequest(
    @Schema(description = "학급 이름")
    String name
) {

}
