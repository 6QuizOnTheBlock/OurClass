package com.quiz.ourclass.domain.challenge.service;

import com.quiz.ourclass.domain.challenge.dto.response.MatchingRoomResponse;

public interface GroupService {

    MatchingRoomResponse createMatchingRoom(long challengeId);

    boolean joinMatchingRoom(String key, boolean joinStatus);

    long createGroup(String key);

    void deleteMatchingMember(String key, Long memberId);

    void inviteMatchingRoom(String key, Long memberId);
}
