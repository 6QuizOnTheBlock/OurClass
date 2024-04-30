package com.quiz.ourclass.domain.challenge.dto.response;

import com.quiz.ourclass.global.dto.MemberSimpleDTO;
import java.util.List;

public record GroupMatchingResponse(
    long id,
    int headCount,
    long leaderId,
    List<MemberSimpleDTO> students
) {

}
