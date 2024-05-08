package com.quiz.ourclass.domain.chat.service;

import com.quiz.ourclass.domain.chat.dto.Message;

public interface ChatService {

    void sendMessage(Message message, String accessToken);
}
