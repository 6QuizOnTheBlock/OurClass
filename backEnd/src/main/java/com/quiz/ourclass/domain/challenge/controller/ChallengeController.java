package com.quiz.ourclass.domain.challenge.controller;

import com.quiz.ourclass.domain.challenge.dto.ChallengSliceRequest;
import com.quiz.ourclass.domain.challenge.dto.ChallengeRequest;
import com.quiz.ourclass.domain.challenge.dto.ChallengeSliceResponse;
import com.quiz.ourclass.domain.challenge.service.ChallengeService;
import com.quiz.ourclass.global.dto.ResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/challenge")
@RequiredArgsConstructor
public class ChallengeController implements ChallengeControllerDocs {

    private final ChallengeService challengeService;

    @GetMapping
    public ResponseEntity<ResultResponse<?>> getChallenges(
        ChallengSliceRequest challengSliceRequest) {
        ChallengeSliceResponse challengeSliceResponse = challengeService.getChallenges(
            challengSliceRequest);
        return ResponseEntity.ok(ResultResponse.success(challengeSliceResponse));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<?>> createChallenge(
        @RequestBody ChallengeRequest challengeRequest) {
        return ResponseEntity.ok(
            ApiResponse.success(challengeService.createChallenge(challengeRequest)));
    }
}
