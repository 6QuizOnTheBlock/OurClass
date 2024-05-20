package com.quiz.ourclass.domain.challenge.service;

import com.quiz.ourclass.domain.challenge.dto.request.AutoGroupMatchingRequest;
import com.quiz.ourclass.domain.challenge.dto.response.AutoGroupMatchingResponse;
import com.quiz.ourclass.domain.challenge.dto.response.MatchingRoomResponse;
import java.util.List;

public interface GroupService {

    MatchingRoomResponse createMatchingRoom(long challengeId);

    boolean joinMatchingRoom(String key, boolean joinStatus);

    long createGroup(String key);

    void deleteMatchingMember(String key, Long memberId);

    void inviteMatchingRoom(String key, Long memberId);

    List<AutoGroupMatchingResponse> getGroupMatching(
        AutoGroupMatchingRequest autoGroupMatchingRequest);

    List<AutoGroupMatchingResponse> testMethod();
}
