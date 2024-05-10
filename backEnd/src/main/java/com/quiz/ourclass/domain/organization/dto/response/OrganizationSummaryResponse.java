package com.quiz.ourclass.domain.organization.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;

@Schema(description = "학급 통계 조회 응답 DTO")
@Builder
public record OrganizationSummaryResponse(
    @Schema(description = "멤버별 함께달리기 참여 횟수 내림차순")
    List<MemberPlayCountResponse> challengeCounts,
    @Schema(description = "멤버별 이어달리기 참여 횟수 내림차순")
    List<MemberPlayCountResponse> relayCounts,
    @Schema(description = "멤버별 게시글 작성 횟수 내림차순")
    List<MemberPlayCountResponse> postCounts
) {

}
