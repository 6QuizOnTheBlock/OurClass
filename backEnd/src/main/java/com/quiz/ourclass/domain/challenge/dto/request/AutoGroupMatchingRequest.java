package com.quiz.ourclass.domain.challenge.dto.request;

import com.quiz.ourclass.domain.challenge.dto.GroupMatchingType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "그룹 자동 매칭 요청 DTO")
public record AutoGroupMatchingRequest(
    @Schema(description = "학급 id")
    long organizationId,
    @Schema(description = "그룹당 최소 인원")
    int minCount,
    @Schema(description = "그룹 매칭 타입")
    GroupMatchingType matchingType,
    @Schema(description = "그룹 참여 멤버")
    List<Long> members
) {

}
