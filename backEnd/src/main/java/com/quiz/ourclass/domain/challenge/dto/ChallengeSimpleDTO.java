package com.quiz.ourclass.domain.challenge.dto;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class ChallengeSimpleDTO {

    private long id;
    private String title;
    private String content;
    private int headCount;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
