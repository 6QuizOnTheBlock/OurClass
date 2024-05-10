package com.quiz.ourclass.domain.organization.dto.response;

import java.util.List;

public record OrganizationHomeResponse(
    String name,
    String photo,
    String organizationName,
    int exp,
    int notifyCount,
    List<RelationSimpleResponse> relations
) {

}
