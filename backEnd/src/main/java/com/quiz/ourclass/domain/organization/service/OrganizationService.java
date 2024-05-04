package com.quiz.ourclass.domain.organization.service;

import com.quiz.ourclass.domain.organization.dto.OrganizationRequest;

public interface OrganizationService {

    Long createOrganization(OrganizationRequest organizationRequest);
}
