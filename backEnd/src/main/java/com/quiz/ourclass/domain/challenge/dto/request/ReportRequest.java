package com.quiz.ourclass.domain.challenge.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "레포트 생성 DTO")
public record ReportRequest(
    @Schema(description = "그룹 id")
    long groupId,
    @Schema(description = "내용")
    String content
) {

}
