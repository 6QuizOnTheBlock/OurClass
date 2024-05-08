package com.quiz.ourclass.domain.chat.entity;

import jakarta.persistence.Id;
import java.time.LocalDateTime;
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
    private String content;
    private LocalDateTime sendDateTime;
    private long readCount;
}
