package com.quiz.ourclass.domain.chat.dto.response;

import com.quiz.ourclass.domain.chat.dto.ChatDTO;
import java.util.List;
import lombok.Builder;

@Builder
public record MessageResponse(
    Long roomId,
    Boolean hasNext,
    List<ChatDTO> messages
) {

}
