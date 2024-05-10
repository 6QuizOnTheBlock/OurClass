package com.quiz.ourclass.domain.organization.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "학급 홈 조회 응답 DTO")
public record OrganizationHomeResponse(
    @Schema(description = "멤버 이름")
    String name,
    @Schema(description = "멤버 사진")
    String photo,
    @Schema(description = "학급 이름")
    String organizationName,
    @Schema(description = "멤버 포인트")
    int exp,
    @Schema(description = "안읽은 알림 갯수")
    int notifyCount,
    @Schema(description = "친한 친구 목록")
    List<RelationSimpleResponse> relations
) {

}
