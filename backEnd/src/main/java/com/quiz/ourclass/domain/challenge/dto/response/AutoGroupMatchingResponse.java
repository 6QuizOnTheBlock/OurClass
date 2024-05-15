package com.quiz.ourclass.domain.challenge.dto.response;

import com.quiz.ourclass.global.dto.MemberSimpleDTO;
import java.util.List;
import lombok.Builder;

@Builder
public record AutoGroupMatchingResponse(
    List<MemberSimpleDTO> members,
    int headCount
) {

}
