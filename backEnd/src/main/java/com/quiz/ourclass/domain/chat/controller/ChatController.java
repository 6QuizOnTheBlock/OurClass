package com.quiz.ourclass.domain.chat.controller;

import com.quiz.ourclass.domain.chat.dto.Message;
import com.quiz.ourclass.domain.chat.dto.request.MessageSliceRequest;
import com.quiz.ourclass.domain.chat.dto.response.MessageResponse;
import com.quiz.ourclass.domain.chat.service.ChatRoomService;
import com.quiz.ourclass.domain.chat.service.ChatService;
import com.quiz.ourclass.global.dto.ResultResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@CrossOrigin("*")
@RestController
@RequestMapping("/chats")
@RequiredArgsConstructor
public class ChatController implements ChatControllerDocs {

    private final ChatService chatService;
    private final ChatRoomService chatRoomService;

    @MessageMapping("/chat/message")
    public void sendMessage(Message message, @Header("Authorization") final String accessToken) {
        chatService.sendMessage(message, accessToken);
    }

    @PatchMapping("/room/{id}")
    public void exitChatRoom(@PathVariable(value = "id") Long id) {
        chatRoomService.exitChatRoom(id);
    }

    @GetMapping
    public ResponseEntity<ResultResponse<MessageResponse>> chatListView(
        MessageSliceRequest request) {
        MessageResponse messageResponse = chatService.chatListView(request);
        return ResponseEntity.ok(ResultResponse.success(messageResponse));
    }
}
