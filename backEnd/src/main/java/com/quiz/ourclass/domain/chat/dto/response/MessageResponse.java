package com.quiz.ourclass.domain.chat.dto.response;

import com.quiz.ourclass.domain.chat.dto.ChatDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;

@Builder
@Schema(description = "채팅 메시지 내역 응답 DTO")
public record MessageResponse(
    @Schema(description = "채팅방 ID", example = "1")
    Long roomId,
    @Schema(description = "다음 페이지 존재 여부", example = "true")
    Boolean hasNext,
    @Schema(description = "채팅 메시지")
    List<ChatDTO> messages
) {

}
