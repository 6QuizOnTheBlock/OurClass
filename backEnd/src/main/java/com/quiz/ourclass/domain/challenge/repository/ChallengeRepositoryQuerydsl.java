package com.quiz.ourclass.domain.challenge.repository;

import com.quiz.ourclass.domain.challenge.dto.request.ChallengeSliceRequest;
import com.quiz.ourclass.domain.challenge.dto.response.ChallengeResponse;
import com.quiz.ourclass.domain.challenge.dto.response.ChallengeSliceResponse;
import com.quiz.ourclass.domain.member.entity.Member;
import com.quiz.ourclass.domain.organization.entity.Organization;

public interface ChallengeRepositoryQuerydsl {

    ChallengeSliceResponse getChallenges(ChallengeSliceRequest challengeSliceRequest);

    ChallengeResponse getChallengeDetail(long id, Long groupId);

    Long countParticipateChallenge(Member member, Organization organization);
}
