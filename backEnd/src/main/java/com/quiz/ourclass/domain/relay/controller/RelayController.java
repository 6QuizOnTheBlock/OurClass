package com.quiz.ourclass.domain.relay.controller;

import com.quiz.ourclass.domain.relay.dto.request.ReceiveRelayRequest;
import com.quiz.ourclass.domain.relay.dto.request.RelayRequest;
import com.quiz.ourclass.domain.relay.dto.request.RelaySliceRequest;
import com.quiz.ourclass.domain.relay.dto.response.ReceiveRelayResponse;
import com.quiz.ourclass.domain.relay.dto.response.RelayResponse;
import com.quiz.ourclass.domain.relay.dto.response.RelaySliceResponse;
import com.quiz.ourclass.domain.relay.dto.response.RunningRelayResponse;
import com.quiz.ourclass.domain.relay.dto.response.SendRelayResponse;
import com.quiz.ourclass.domain.relay.service.RelayService;
import com.quiz.ourclass.global.dto.ResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/relays")
@RestController
public class RelayController implements RelayControllerDocs {

    private final RelayService relayService;

    @PostMapping
    public ResponseEntity<ResultResponse<?>> createRelay(@RequestBody RelayRequest relayRequest) {
        long relayId = relayService.createRelay(relayRequest);
        return ResponseEntity.ok(ResultResponse.success(relayId));
    }

    @GetMapping
    public ResponseEntity<ResultResponse<?>> getRelays(RelaySliceRequest relaySliceRequest) {
        RelaySliceResponse relays = relayService.getRelays(relaySliceRequest);
        return ResponseEntity.ok(ResultResponse.success(relays));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResultResponse<?>> getRelayDetail(@PathVariable long id) {
        RelayResponse relay = relayService.getRelayDetail(id);
        return ResponseEntity.ok(ResultResponse.success(relay));
    }

    @GetMapping("/running")
    public ResponseEntity<ResultResponse<?>> getRunningRelay(
        @RequestParam(required = true) long organizationId) {
        RunningRelayResponse runningChallengeResponse = relayService.getRunningRelay(
            organizationId);
        return ResponseEntity.ok(ResultResponse.success(runningChallengeResponse));
    }

    @PostMapping("/{id}/receive")
    public ResponseEntity<ResultResponse<?>> receiveRelay(@PathVariable long id,
        @RequestBody ReceiveRelayRequest receiveRelayRequest) {
        ReceiveRelayResponse receiveRelayResponse = relayService.receiveRelay(
            id, receiveRelayRequest);
        return ResponseEntity.ok(ResultResponse.success(receiveRelayResponse));
    }

    @PostMapping("/{id}/send")
    public ResponseEntity<ResultResponse<?>> sendRelay(@PathVariable long id) {
        SendRelayResponse sendRelayResponse = relayService.sendRelay(id);
        return ResponseEntity.ok(ResultResponse.success(sendRelayResponse));
    }

    @GetMapping("/{id}/question")
    public ResponseEntity<ResultResponse<?>> getRelayQuestion(@PathVariable long id) {
        String question = relayService.getRelayQuestion(id);
        return ResponseEntity.ok(ResultResponse.success(question));
    }
}
