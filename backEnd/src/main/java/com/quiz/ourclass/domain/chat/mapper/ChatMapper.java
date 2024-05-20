package com.quiz.ourclass.domain.chat.mapper;

import com.quiz.ourclass.domain.chat.dto.ChatDTO;
import com.quiz.ourclass.domain.chat.dto.response.MessageResponse;
import com.quiz.ourclass.domain.chat.entity.Chat;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ChatMapper {

    ChatDTO chatToChatDTO(Chat chat);

    MessageResponse messagesToMessageResponse(Long roomId, boolean hasNext, List<ChatDTO> messages);
}
