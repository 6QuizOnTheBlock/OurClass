package com.quiz.ourclass.domain.relay.dto.response;

import java.util.List;

public record RelayResponse(
    RelaySimpleResponse relaySimple,
    List<RelayMemberResponse> runners
) {

}
