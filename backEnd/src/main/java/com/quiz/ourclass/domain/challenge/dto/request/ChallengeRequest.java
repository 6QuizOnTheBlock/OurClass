package com.quiz.ourclass.domain.challenge.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "함께달리기 생성 요청 DTO")
public record ChallengeRequest(
    @Schema(description = "학급 id")
    long organizationId,
    @Schema(description = "함께달리기 제목")
    String title,
    @Schema(description = "함께달리기 내용")
    String content,
    @Schema(description = "함께달리기 시작시간")
    LocalDateTime startTime,
    @Schema(description = "함께달리기 종료시간")
    LocalDateTime endTime,
    @Schema(description = "보상 경험치")
    int reward,
    @Schema(description = "그룹 당 최소인원")
    int minCount,
    @Schema(description = "함께달리기 참여 그룹")
    List<GroupMatchingRequest> groups
) {

}
