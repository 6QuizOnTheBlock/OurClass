package com.quiz.ourclass.domain.relay.dto.response;

import lombok.Builder;

@Builder
public record SendRelayResponse(
    String prevMemberName,
    String prevQuestion
) {

}
