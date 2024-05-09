package com.quiz.ourclass.domain.organization.dto.response;

import lombok.Builder;

@Builder
public record MemberDetailResponse(
    String name,
    String photo,
    double isolationPoint,
    int exp,
    long challengeCount,
    long relayCount,
    long postCount
) {

}
