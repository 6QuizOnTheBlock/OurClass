package com.quiz.ourclass.domain.challenge.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
@Schema(description = "함께달리기 DTO")
public class ChallengeSimpleDTO {

    @Schema(description = "함께달리기 ID")
    private long id;
    @Schema(description = "함께달리기 제목")
    private String title;
    @Schema(description = "함께달리기 내용")
    private String content;
    @Schema(description = "함께달리기 참여자 수")
    private int headCount;
    @Schema(description = "함께달리기 시작 시간")
    private LocalDateTime startTime;
    @Schema(description = "함께달리기 종료 시간")
    private LocalDateTime endTime;
}
