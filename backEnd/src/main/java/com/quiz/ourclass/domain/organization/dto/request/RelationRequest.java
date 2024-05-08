package com.quiz.ourclass.domain.organization.dto.request;

public record RelationRequest(
    long sourceMemberId,
    long targetMemberId
) {

}
