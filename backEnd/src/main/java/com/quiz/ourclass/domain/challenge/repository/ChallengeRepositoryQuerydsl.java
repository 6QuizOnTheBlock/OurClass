package com.quiz.ourclass.domain.challenge.repository;

import com.quiz.ourclass.domain.challenge.dto.ChallengSliceRequest;
import com.quiz.ourclass.domain.challenge.dto.ChallengeSliceResponse;

public interface ChallengeRepositoryQuerydsl {

    ChallengeSliceResponse getChallenges(ChallengSliceRequest challengSliceRequest);
}
