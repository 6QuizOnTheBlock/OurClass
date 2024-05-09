package com.quiz.ourclass.domain.chat.mapper;

import com.quiz.ourclass.domain.chat.dto.ChatDTO;
import com.quiz.ourclass.domain.chat.entity.Chat;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ChatMapper {

    ChatDTO chatToChatDTO(Chat chat);
}
