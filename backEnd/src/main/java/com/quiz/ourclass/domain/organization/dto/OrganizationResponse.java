package com.quiz.ourclass.domain.organization.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;

@Schema(description = "학급 조회 응답 DTO")
public record OrganizationResponse(
    @Schema(description = "학급 id")
    long id,
    @Schema(description = "학급 이름")
    String name,
    @Schema(description = "학급 멤버 수")
    int memberCount,
    @Schema(description = "학급 생성 날짜")
    LocalDate createTime
) {

}
