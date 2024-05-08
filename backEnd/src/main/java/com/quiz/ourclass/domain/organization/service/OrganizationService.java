package com.quiz.ourclass.domain.organization.service;

import com.quiz.ourclass.domain.organization.dto.InviteCodeDTO;
import com.quiz.ourclass.domain.organization.dto.OrganizationRequest;
import com.quiz.ourclass.domain.organization.dto.OrganizationResponse;
import com.quiz.ourclass.domain.organization.dto.request.UpdateOrganizationRequest;
import com.quiz.ourclass.domain.organization.dto.response.MemberRankPoint;
import com.quiz.ourclass.domain.organization.dto.response.UpdateOrganizationResponse;
import com.quiz.ourclass.global.dto.MemberSimpleDTO;
import java.util.List;

public interface OrganizationService {

    Long createOrganization(OrganizationRequest organizationRequest);

    List<OrganizationResponse> getOrganizations();

    InviteCodeDTO getOrganizationCode(long id);

    Long joinOrganization(long id, InviteCodeDTO inviteCodeDTO);

    List<MemberSimpleDTO> getOrganizationMembers(long id);

    UpdateOrganizationResponse updateOrganizationName(
        long id, UpdateOrganizationRequest updateOrganizationRequest);

    List<MemberRankPoint> getRanking(long id);
}
