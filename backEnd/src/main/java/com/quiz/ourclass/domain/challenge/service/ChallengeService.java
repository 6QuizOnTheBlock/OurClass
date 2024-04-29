package com.quiz.ourclass.domain.challenge.service;

import com.quiz.ourclass.domain.challenge.dto.ChallengSliceRequest;
import com.quiz.ourclass.domain.challenge.dto.ChallengeSliceResponse;

public interface ChallengeService {

    ChallengeSliceResponse getChallenges(ChallengSliceRequest challengSliceRequest);
}
