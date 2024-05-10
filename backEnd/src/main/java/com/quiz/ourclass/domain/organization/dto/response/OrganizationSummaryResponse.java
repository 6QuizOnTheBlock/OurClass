package com.quiz.ourclass.domain.organization.dto.response;

import java.util.List;
import lombok.Builder;

@Builder
public record OrganizationSummaryResponse(
    List<MemberPlayCountResponse> challengeCounts,
    List<MemberPlayCountResponse> relayCounts,
    List<MemberPlayCountResponse> postCounts
) {

}
