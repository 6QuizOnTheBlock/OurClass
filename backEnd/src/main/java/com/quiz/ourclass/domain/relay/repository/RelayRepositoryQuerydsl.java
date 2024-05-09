package com.quiz.ourclass.domain.relay.repository;

import com.quiz.ourclass.domain.member.entity.Member;
import com.quiz.ourclass.domain.organization.entity.Organization;
import com.quiz.ourclass.domain.relay.dto.request.RelaySliceRequest;
import com.quiz.ourclass.domain.relay.dto.response.RelayResponse;
import com.quiz.ourclass.domain.relay.dto.response.RelaySliceResponse;

public interface RelayRepositoryQuerydsl {

    RelaySliceResponse getRelays(RelaySliceRequest relaySliceRequest);

    RelayResponse getRelayDetail(long id);

    Long countParticipateRelay(Member member, Organization organization);
}
