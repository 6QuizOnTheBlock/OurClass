package com.quiz.ourclass.domain.chat.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "채팅 메시지 상세 응답 DTO")
public record ChatDTO(
    @Schema(description = "채팅 전송한 멤버 ID", example = "32")
    Long memberId,
    @Schema(description = "채팅 전송한 멤버 이름", example = "홍유준")
    String memberName,
    @Schema(description = "채팅방 전송한 멤버 이미지 URL", example = "www.image.com")
    String memberImageUrl,
    @Schema(description = "채팅 메시지 내용", example = "아 STOMP 진짜 짜증난다 진짜!!!!!! 채팅 하기싫어...")
    String content,
    @Schema(description = "채팅 전송 시간(밀리 세컨드)", example = "1")
    Long sendDateTime,
    @Schema(description = "채팅 메시지 안 읽은 개수", example = "1")
    Long readCount
) {


}
