package com.quiz.ourclass.domain.notice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
@Schema(description = "sse 전송을 위한 DTO")
public record SseDTO(

    @Schema(description = "이벤트 종류")
    SseType eventType,
    @Schema(description = "이벤트 수신자 id")
    Long receiverId,
    @Schema(description = "이벤트 발신자 or 관련 위치")
    String url,
    @Schema(description = "이벤트 관련 데이터")
    String data,
    @Schema(description = "이벤트 전송 시간")
    LocalDateTime time
) {

}
