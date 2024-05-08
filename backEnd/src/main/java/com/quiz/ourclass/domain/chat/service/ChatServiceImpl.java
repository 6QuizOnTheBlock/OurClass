package com.quiz.ourclass.domain.chat.service;

import com.quiz.ourclass.domain.chat.dto.Message;
import com.quiz.ourclass.domain.chat.service.message.MessageSend;
import com.quiz.ourclass.domain.member.entity.Member;
import com.quiz.ourclass.domain.member.repository.MemberRepository;
import com.quiz.ourclass.global.exception.ErrorCode;
import com.quiz.ourclass.global.exception.GlobalException;
import com.quiz.ourclass.global.util.ConstantUtil;
import com.quiz.ourclass.global.util.RedisUtil;
import com.quiz.ourclass.global.util.jwt.JwtUtil;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final MemberRepository memberRepository;
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
}
