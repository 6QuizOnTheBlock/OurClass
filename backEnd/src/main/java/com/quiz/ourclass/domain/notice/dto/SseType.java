package com.quiz.ourclass.domain.notice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SseType {
    SSE_CONNECT, INVITE_REQUEST, INVITE_RESPONSE, KICK_MEMBER, CREATE_GROUP;
}
