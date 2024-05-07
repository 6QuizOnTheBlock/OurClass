package com.quiz.ourclass.domain.challenge.repository;

import com.quiz.ourclass.domain.challenge.dto.request.ChallengeSliceRequest;
import com.quiz.ourclass.domain.challenge.dto.response.ChallengeResponse;
import com.quiz.ourclass.domain.challenge.dto.response.ChallengeSliceResponse;

public interface ChallengeRepositoryQuerydsl {

    ChallengeSliceResponse getChallenges(ChallengeSliceRequest challengeSliceRequest);

    ChallengeResponse getChallengeDetail(long id, Long groupId);
}
