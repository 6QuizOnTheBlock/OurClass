package com.quiz.ourclass.domain.organization.dto;

import java.time.LocalDate;

public record OrganizationResponse(
    long id,
    String name,
    int memberCount,
    LocalDate createTime
) {

}
