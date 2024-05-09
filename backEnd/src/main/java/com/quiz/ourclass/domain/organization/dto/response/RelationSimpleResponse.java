package com.quiz.ourclass.domain.organization.dto.response;

import com.quiz.ourclass.global.dto.MemberSimpleDTO;
import lombok.Builder;

@Builder
public record RelationSimpleResponse(
    MemberSimpleDTO member,
    int relationPoint
) {

}
