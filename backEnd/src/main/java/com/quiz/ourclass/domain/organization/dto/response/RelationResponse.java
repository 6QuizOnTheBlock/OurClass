package com.quiz.ourclass.domain.organization.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "관계 조회 응답 DTO")
public record RelationResponse(
    @Schema(description = "대상 멤버 id")
    long memberId,
    @Schema(description = "대상 멤버 이름")
    String memberName,
    @Schema(description = "관계 포인트")
    int relationPoint,
    @Schema(description = "태깅 횟수")
    int tagGreetingCount,
    @Schema(description = "그룹 횟수")
    int groupCount,
    @Schema(description = "이어달리기 질문 받은 횟수")
    int receiveCount,
    @Schema(description = "이어달리기 질문 준 횟수")
    int sendCount
) {

}
