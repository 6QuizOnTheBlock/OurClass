package com.quiz.ourclass.domain.quiz.dto.request;

import com.quiz.ourclass.domain.quiz.dto.QuizDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "퀴즈 만들기 요청")
public record MakingQuizRequest(
    @Schema(description = "단체 아이디")
    long orgId,
    @Schema(description = "문제 총 개수")
    int quizCount,
    @Schema(description = "제한 시간")
    int limitTime,
    @Schema(description = "퀴즈 낱개 상세")
    List<QuizDTO> quizList

) {

}
