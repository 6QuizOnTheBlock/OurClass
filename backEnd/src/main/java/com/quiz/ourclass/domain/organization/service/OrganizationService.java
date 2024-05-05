package com.quiz.ourclass.domain.organization.service;

import com.quiz.ourclass.domain.organization.dto.InviteCodeDTO;
import com.quiz.ourclass.domain.organization.dto.OrganizationRequest;
import com.quiz.ourclass.domain.organization.dto.OrganizationResponse;
import java.util.List;

public interface OrganizationService {

    Long createOrganization(OrganizationRequest organizationRequest);

    List<OrganizationResponse> getOrganizations();

    InviteCodeDTO getOrganizationCode(long id);

    Long joinOrganization(long id, InviteCodeDTO inviteCodeDTO);
}
