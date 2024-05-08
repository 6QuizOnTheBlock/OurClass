package com.quiz.ourclass.domain.chat.controller;

import com.quiz.ourclass.domain.chat.dto.Message;
import com.quiz.ourclass.domain.chat.service.ChatRoomServiceImpl;
import com.quiz.ourclass.domain.chat.service.ChatServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chats")
@RequiredArgsConstructor
public class ChatController implements ChatControllerDocs {

    public final ChatServiceImpl chatServiceImpl;
    public final ChatRoomServiceImpl chatRoomServiceImpl;

    @MessageMapping("/chat/message")
    public void sendMessage(Message message, @Header("Authorization") final String accessToken) {
        chatServiceImpl.sendMessage(message, accessToken);
    }

    @PatchMapping("/room/{id}")
    public void exitChatRoom(@PathVariable(value = "id") Long id) {
        chatRoomServiceImpl.exitChatRoom(id);
    }

}
