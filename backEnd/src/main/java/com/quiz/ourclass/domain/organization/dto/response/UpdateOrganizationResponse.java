package com.quiz.ourclass.domain.organization.dto.response;

import lombok.Builder;

@Builder
public record UpdateOrganizationResponse(
    String name
) {

}
