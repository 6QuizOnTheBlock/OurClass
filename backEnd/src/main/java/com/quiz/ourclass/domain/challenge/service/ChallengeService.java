package com.quiz.ourclass.domain.challenge.service;

import com.quiz.ourclass.domain.challenge.dto.request.ChallengSliceRequest;
import com.quiz.ourclass.domain.challenge.dto.request.ChallengeRequest;
import com.quiz.ourclass.domain.challenge.dto.request.ReportRequest;
import com.quiz.ourclass.domain.challenge.dto.response.ChallengeSliceResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ChallengeService {

    ChallengeSliceResponse getChallenges(ChallengSliceRequest challengSliceRequest);

    long createChallenge(ChallengeRequest challengeRequest);

    long createReport(ReportRequest reportRequest, MultipartFile file);
}
