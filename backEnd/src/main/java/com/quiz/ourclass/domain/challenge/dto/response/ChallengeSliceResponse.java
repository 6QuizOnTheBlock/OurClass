package com.quiz.ourclass.domain.challenge.dto.response;

import com.quiz.ourclass.domain.challenge.dto.ChallengeSimpleDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@Schema(description = "함께달리기 페이징 목록 응답 DTO")
public class ChallengeSliceResponse {

    @Schema(description = "페이지")
    private int page;
    @Schema(description = "페이지 당 갯수")
    private int size;
    @Schema(description = "마지막 페이지 여부")
    private boolean last;
    @Schema(description = "함께달리기 목록")
    private List<ChallengeSimpleDTO> challenges;
}
