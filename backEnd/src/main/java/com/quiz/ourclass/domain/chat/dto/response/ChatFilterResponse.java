package com.quiz.ourclass.domain.chat.dto.response;

import com.quiz.ourclass.domain.chat.dto.ChatFilterDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;

@Builder
@Schema(description = "채팅 필터링 단어 응답 DTO")
public record ChatFilterResponse(
    @Schema(description = "다음 페이지 존재 여부", example = "true")
    Boolean hasNext,

    @Schema(description = "필터링 단어 PK")
    List<ChatFilterDTO> words
) {

}
