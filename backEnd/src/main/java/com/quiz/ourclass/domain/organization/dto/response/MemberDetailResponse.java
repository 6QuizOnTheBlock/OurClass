package com.quiz.ourclass.domain.organization.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "멤버 상세 조회 응답 DTO")
public record MemberDetailResponse(
    @Schema(description = "멤버 이름")
    String name,
    @Schema(description = "멤버 프로필 사진")
    String photo,
    @Schema(description = "멤버 인싸력")
    double isolationPoint,
    @Schema(description = "멤버 포인트")
    int exp,
    @Schema(description = "함께달리기 참여 횟수")
    long challengeCount,
    @Schema(description = "이어달리기 참여 횟수")
    long relayCount,
    @Schema(description = "게시글 작성 횟수")
    long postCount
) {

}
