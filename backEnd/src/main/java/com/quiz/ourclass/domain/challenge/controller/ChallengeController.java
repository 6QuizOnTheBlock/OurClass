package com.quiz.ourclass.domain.challenge.controller;

import com.quiz.ourclass.domain.challenge.dto.request.ChallengSliceRequest;
import com.quiz.ourclass.domain.challenge.dto.request.ChallengeRequest;
import com.quiz.ourclass.domain.challenge.dto.request.ReportRequest;
import com.quiz.ourclass.domain.challenge.dto.response.ChallengeSliceResponse;
import com.quiz.ourclass.domain.challenge.service.ChallengeService;
import com.quiz.ourclass.global.dto.ResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity<ResultResponse<?>> createChallenge(
        @RequestBody ChallengeRequest challengeRequest) {
        return ResponseEntity.ok(
            ResultResponse.success(challengeService.createChallenge(challengeRequest)));
    }

    @PostMapping(value = "/reports", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResultResponse<?>> createReport(
        @RequestPart ReportRequest reportRequest, @RequestPart MultipartFile file) {
        long reportId = challengeService.createReport(reportRequest, file);
        return ResponseEntity.ok(ResultResponse.success(reportId));
    }
}
