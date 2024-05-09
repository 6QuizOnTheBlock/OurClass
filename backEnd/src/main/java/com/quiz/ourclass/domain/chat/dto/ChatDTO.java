package com.quiz.ourclass.domain.chat.dto;

import java.time.LocalDateTime;

public record ChatDTO(
    String id,
    Long memberId,
    String memberName,
    String memberImageUrl,
    String content,
    LocalDateTime sendDateTime,
    Long readCount
) {


}
