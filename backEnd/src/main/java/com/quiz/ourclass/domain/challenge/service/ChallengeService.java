package com.quiz.ourclass.domain.challenge.service;

import com.quiz.ourclass.domain.challenge.dto.request.ChallengSliceRequest;
import com.quiz.ourclass.domain.challenge.dto.request.ChallengeRequest;
import com.quiz.ourclass.domain.challenge.dto.response.ChallengeSliceResponse;

public interface ChallengeService {

    ChallengeSliceResponse getChallenges(ChallengSliceRequest challengSliceRequest);

    long createChallenge(ChallengeRequest challengeRequest);
}
