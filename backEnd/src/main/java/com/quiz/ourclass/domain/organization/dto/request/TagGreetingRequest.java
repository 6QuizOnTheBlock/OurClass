package com.quiz.ourclass.domain.organization.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "태깅인사 요청 DTO")
public record TagGreetingRequest(
    @Schema(description = "학급 id")
    Long organizationId,
    @Schema(description = "상대 멤버 id")
    Long memberId
) {

}
