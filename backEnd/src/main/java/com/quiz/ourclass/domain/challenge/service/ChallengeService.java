package com.quiz.ourclass.domain.challenge.service;

import com.quiz.ourclass.domain.challenge.dto.request.ChallengSliceRequest;
import com.quiz.ourclass.domain.challenge.dto.request.ChallengeRequest;
import com.quiz.ourclass.domain.challenge.dto.request.ReportRequest;
import com.quiz.ourclass.domain.challenge.dto.response.ChallengeResponse;
import com.quiz.ourclass.domain.challenge.dto.response.ChallengeSliceResponse;
import com.quiz.ourclass.domain.challenge.dto.response.RunningChallengeResponse;
import com.quiz.ourclass.domain.challenge.entity.ReportType;
import org.springframework.web.multipart.MultipartFile;

public interface ChallengeService {

    ChallengeSliceResponse getChallenges(ChallengSliceRequest challengSliceRequest);

    long createChallenge(ChallengeRequest challengeRequest);

    long createReport(ReportRequest reportRequest, MultipartFile file);

    void confirmReport(long id, ReportType reportType);

    RunningChallengeResponse getRunningChallenge(long organizationId);

    ChallengeResponse getChallengeDetail(long id, long groupId);
}
