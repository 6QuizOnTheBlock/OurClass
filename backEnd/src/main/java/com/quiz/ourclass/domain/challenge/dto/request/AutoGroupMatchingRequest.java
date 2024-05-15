package com.quiz.ourclass.domain.challenge.dto.request;

import com.quiz.ourclass.domain.challenge.dto.GroupMatchingType;
import java.util.List;

public record AutoGroupMatchingRequest(
    long organizationId,
    int minCount,
    GroupMatchingType matchingType,
    List<Long> members
) {

}
