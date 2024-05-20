package com.quiz.ourclass.domain.relay.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "이어달리기 상세조회 응답 DTO")
public record RelayResponse(
    RelaySimpleResponse relaySimple,
    List<RelayMemberResponse> runners
) {

}
