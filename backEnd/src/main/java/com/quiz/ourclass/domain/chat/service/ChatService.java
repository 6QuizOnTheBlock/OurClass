package com.quiz.ourclass.domain.chat.service;

import com.quiz.ourclass.domain.chat.dto.Message;
import com.quiz.ourclass.domain.chat.dto.request.MessageSliceRequest;
import com.quiz.ourclass.domain.chat.dto.response.MessageResponse;

public interface ChatService {

    void sendMessage(Message message, String accessToken);

    MessageResponse chatListView(MessageSliceRequest request);
}
