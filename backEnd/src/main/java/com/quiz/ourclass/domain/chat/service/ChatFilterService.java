package com.quiz.ourclass.domain.chat.service;

import com.quiz.ourclass.domain.chat.dto.request.ChatFilterRequest;
import com.quiz.ourclass.domain.chat.dto.request.ChatFilterSliceRequest;
import com.quiz.ourclass.domain.chat.dto.response.ChatFilterResponse;

public interface ChatFilterService {

    Long register(Long organizationId, ChatFilterRequest request);

    Boolean modify(Long organizationId, Long chatFilterId, ChatFilterRequest request);

    Boolean delete(Long chatFilterId);

    ChatFilterResponse select(ChatFilterSliceRequest request);
}
