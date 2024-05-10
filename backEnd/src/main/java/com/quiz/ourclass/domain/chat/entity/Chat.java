package com.quiz.ourclass.domain.chat.entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Builder
@AllArgsConstructor
@Document(collection = "chat")
public class Chat {

    @Id
    private String id;
    private Long roomId;
    private Long memberId;
    private String memberName;
    private String memberImageUrl;
    private String content;
    private Long sendDateTime;
    private long readCount;
}
