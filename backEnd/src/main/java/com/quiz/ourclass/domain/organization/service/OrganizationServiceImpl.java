package com.quiz.ourclass.domain.organization.service;

import com.quiz.ourclass.domain.member.entity.Member;
import com.quiz.ourclass.domain.member.entity.Role;
import com.quiz.ourclass.domain.organization.dto.OrganizationRequest;
import com.quiz.ourclass.domain.organization.dto.OrganizationResponse;
import com.quiz.ourclass.domain.organization.entity.MemberOrganization;
import com.quiz.ourclass.domain.organization.entity.Organization;
import com.quiz.ourclass.domain.organization.mapper.OrganizationMapper;
import com.quiz.ourclass.domain.organization.repository.MemberOrganizationRepository;
import com.quiz.ourclass.domain.organization.repository.OrganizationRepository;
import com.quiz.ourclass.global.util.UserAccessUtil;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final MemberOrganizationRepository memberOrganizationRepository;
    private final OrganizationMapper organizationMapper;
    private final UserAccessUtil accessUtil;

    @Override
    public Long createOrganization(OrganizationRequest organizationRequest) {
        Member member = accessUtil.getMember();
        Organization organization = Organization.builder()
            .name(organizationRequest.name())
            .manager(member)
            .createTime(LocalDate.now())
            .build();
        return organizationRepository.save(organization).getId();
    }

    @Override
    public List<OrganizationResponse> getOrganizations() {
        Member member = accessUtil.getMember();
        if (member.getRole().equals(Role.TEACHER)) {
            List<Organization> organizations = organizationRepository.findByManager(member);
            return organizations.stream()
                .map(organizationMapper::organizationToOrganizationResponse)
                .toList();
        }
        List<MemberOrganization> memberOrganizations = memberOrganizationRepository.findByMember(
            member);
        return memberOrganizations.stream()
            .map(MemberOrganization::getOrganization)
            .map(organizationMapper::organizationToOrganizationResponse)
            .toList();
    }
}
