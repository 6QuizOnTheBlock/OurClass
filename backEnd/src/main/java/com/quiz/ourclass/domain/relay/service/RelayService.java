package com.quiz.ourclass.domain.relay.service;

import com.quiz.ourclass.domain.relay.dto.request.RelayRequest;
import com.quiz.ourclass.domain.relay.dto.request.RelaySliceRequest;
import com.quiz.ourclass.domain.relay.dto.response.RelayResponse;
import com.quiz.ourclass.domain.relay.dto.response.RelaySliceResponse;
import com.quiz.ourclass.domain.relay.dto.response.RunningRelayResponse;

public interface RelayService {

    long createRelay(RelayRequest relayRequest);

    RelaySliceResponse getRelays(RelaySliceRequest relaySliceRequest);

    RelayResponse getRelayDetail(long id);

    RunningRelayResponse getRunningRelay(long organizationId);
}
