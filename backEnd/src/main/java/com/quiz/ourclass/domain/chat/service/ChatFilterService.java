package com.quiz.ourclass.domain.chat.service;

import com.quiz.ourclass.domain.chat.dto.request.ChatFilterRequest;

public interface ChatFilterService {

    Long register(Long organizationId, ChatFilterRequest request);

    Boolean modify(Long organizationId, Long chatFilterId, ChatFilterRequest request);
}
