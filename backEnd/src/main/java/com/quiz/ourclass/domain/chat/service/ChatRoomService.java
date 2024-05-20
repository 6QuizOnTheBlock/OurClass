package com.quiz.ourclass.domain.chat.service;

import com.quiz.ourclass.domain.organization.entity.Organization;

public interface ChatRoomService {

    void createChatRoom(Organization organization);

    void exitChatRoom(Long roomId);

    void isMemberAuthorizedToJoinRoom(Long memberId, Long chatRoomId);
}
