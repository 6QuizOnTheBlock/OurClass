package com.quiz.ourclass.domain.chat.service;

import com.quiz.ourclass.domain.chat.dto.ChatDTO;
import com.quiz.ourclass.domain.chat.dto.Message;
import com.quiz.ourclass.domain.chat.dto.request.MessageSliceRequest;
import com.quiz.ourclass.domain.chat.dto.response.MessageResponse;
import com.quiz.ourclass.domain.chat.entity.Chat;
import com.quiz.ourclass.domain.chat.mapper.ChatMapper;
import com.quiz.ourclass.domain.chat.repository.ChatRepository;
import com.quiz.ourclass.domain.chat.service.message.MessageSend;
import com.quiz.ourclass.domain.member.entity.Member;
import com.quiz.ourclass.domain.member.repository.MemberRepository;
import com.quiz.ourclass.global.exception.ErrorCode;
import com.quiz.ourclass.global.exception.GlobalException;
import com.quiz.ourclass.global.util.ConstantUtil;
import com.quiz.ourclass.global.util.RedisUtil;
import com.quiz.ourclass.global.util.jwt.JwtUtil;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final MemberRepository memberRepository;
    private final ChatRepository chatRepository;
    private final ChatMapper chatMapper;
    private final RedisUtil redisUtil;
    private final JwtUtil jwtUtil;
    private final MessageSend messageSend;

    @Transactional
    public void sendMessage(Message message, String accessToken) {
        Member member = memberRepository.findById(
            Long.valueOf(jwtUtil.getUserInfoFromToken(accessToken).getSubject())
        ).orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_NOT_FOUND));

        int readCount = redisUtil.getChatRoomUsers(message.getRoomId()).size();

        message.setSendTimeAndSender(
            LocalDateTime.now(),
            member,
            readCount
        );

        messageSend.send(ConstantUtil.KAFKA_TOPIC, message);
    }

    @Override
    public MessageResponse chatListView(MessageSliceRequest request) {
        Pageable pageable = PageRequest.of(request.page(), request.size());
        Long roomId = request.roomId();
        Page<Chat> chats = chatRepository.findByRoomIdOrderByIdDesc(
            roomId, pageable
        );
        List<ChatDTO> chatDTOList = chats.stream()
            .map(chatMapper::chatToChatDTO)
            .toList();

        return MessageResponse.builder()
            .roomId(roomId)
            .messages(chatDTOList)
            .hasNext(chats.hasNext())
            .build();
    }
}
