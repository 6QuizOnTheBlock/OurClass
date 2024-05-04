package com.quiz.ourclass.domain.organization.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "학급 생성 요청 DTO")
public record OrganizationRequest(
    @Schema(description = "학급 이름")
    String name
) {

}
