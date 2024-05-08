package com.quiz.ourclass.domain.organization.dto.response;

import lombok.Builder;

@Builder
public record RelationResponse(
    long memberId,
    String memberName,
    int relationPoint,
    int tagCount,
    int groupCount,
    int receiveCount,
    int sendCount
) {

}
