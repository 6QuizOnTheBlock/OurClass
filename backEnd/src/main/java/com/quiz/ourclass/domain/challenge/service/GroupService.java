package com.quiz.ourclass.domain.challenge.service;

public interface GroupService {

    String createMatchingRoom(long challengeId);

    boolean joinMatchingRoom(String key, boolean joinStatus);
}
