package com.quiz.ourclass.domain.challenge.repository;

import com.quiz.ourclass.domain.challenge.dto.request.ChallengSliceRequest;
import com.quiz.ourclass.domain.challenge.dto.response.ChallengeResponse;
import com.quiz.ourclass.domain.challenge.dto.response.ChallengeSliceResponse;

public interface ChallengeRepositoryQuerydsl {

    ChallengeSliceResponse getChallenges(ChallengSliceRequest challengSliceRequest);

    ChallengeResponse getChallengeDetail(long id, Long groupId);
}
