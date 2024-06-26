package com.quiz.ourclass.domain.chat.dto;

import com.quiz.ourclass.domain.chat.entity.Chat;
import com.quiz.ourclass.domain.member.entity.Member;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Message implements Serializable {

    @NotNull
    private Long roomId;
    private Long memberId;
    private String memberName;
    private String memberImageUrl;
    @NotNull
    private String content;
    private Long sendDateTime;
    private Integer readCount;

    public void setSendTimeAndSender(
        LocalDateTime sendDateTime, Member member, Integer readCount
    ) {
        this.memberName = member.getName();
        this.sendDateTime = sendDateTime.atZone(ZoneId.of("Asia/Seoul")).toInstant().toEpochMilli();
        this.memberId = member.getId();
        this.readCount = readCount;
    }

    public void convertContent(String content) {
        this.content = content;
    }

    public Chat convertEntity() {
        return Chat.builder()
            .roomId(roomId)
            .memberId(memberId)
            .memberName(memberName)
            .memberImageUrl(memberImageUrl)
            .content(content)
            .sendDateTime(sendDateTime)
            .readCount(readCount)
            .build();
    }
}
