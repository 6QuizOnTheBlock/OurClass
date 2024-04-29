package com.quiz.ourclass.domain.challenge.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ChallengeSliceResponse {

    private int page;
    private int size;
    private boolean last;
    private List<ChallengeSimpleDTO> challenges;
}
