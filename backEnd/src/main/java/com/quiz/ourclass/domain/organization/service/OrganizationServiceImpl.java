package com.quiz.ourclass.domain.organization.service;

import com.quiz.ourclass.domain.member.entity.Member;
import com.quiz.ourclass.domain.member.entity.Role;
import com.quiz.ourclass.domain.member.mapper.MemberMapper;
import com.quiz.ourclass.domain.organization.dto.InviteCodeDTO;
import com.quiz.ourclass.domain.organization.dto.OrganizationRequest;
import com.quiz.ourclass.domain.organization.dto.OrganizationResponse;
import com.quiz.ourclass.domain.organization.dto.request.UpdateOrganizationRequest;
import com.quiz.ourclass.domain.organization.dto.response.UpdateOrganizationResponse;
import com.quiz.ourclass.domain.organization.entity.MemberOrganization;
import com.quiz.ourclass.domain.organization.entity.Organization;
import com.quiz.ourclass.domain.organization.mapper.OrganizationMapper;
import com.quiz.ourclass.domain.organization.repository.MemberOrganizationRepository;
import com.quiz.ourclass.domain.organization.repository.OrganizationRepository;
import com.quiz.ourclass.global.dto.MemberSimpleDTO;
import com.quiz.ourclass.global.exception.ErrorCode;
import com.quiz.ourclass.global.exception.GlobalException;
import com.quiz.ourclass.global.util.RedisUtil;
import com.quiz.ourclass.global.util.UserAccessUtil;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final MemberOrganizationRepository memberOrganizationRepository;
    private final OrganizationMapper organizationMapper;
    private final MemberMapper memberMapper;
    private final RedisUtil redisUtil;
    private final UserAccessUtil accessUtil;
    private final static String REDIS_ORG_KEY = "ORGANIZATION:";
    private final static int REDIS_ORG_ALIVE_MINUTE = 10;

    @Transactional
    @Override
    public Long createOrganization(OrganizationRequest organizationRequest) {
        Member member = accessUtil.getMember()
            .orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_NOT_FOUND));
        Organization organization = Organization.builder()
            .name(organizationRequest.name())
            .manager(member)
            .createTime(LocalDate.now())
            .build();
        return organizationRepository.save(organization).getId();
    }

    @Override
    public List<OrganizationResponse> getOrganizations() {
        Member member = accessUtil.getMember()
            .orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_NOT_FOUND));
        if (member.getRole().equals(Role.TEACHER)) {
            List<Organization> organizations = organizationRepository.findByManager(member);
            return organizations.stream()
                .map(organizationMapper::organizationToOrganizationResponse)
                .sorted((o1, o2) -> o2.createTime().compareTo(o1.createTime()))
                .toList();
        }
        List<MemberOrganization> memberOrganizations = memberOrganizationRepository.findByMember(
            member);
        return memberOrganizations.stream()
            .map(MemberOrganization::getOrganization)
            .map(organizationMapper::organizationToOrganizationResponse)
            .sorted((o1, o2) -> o2.createTime().compareTo(o1.createTime()))
            .toList();
    }

    @Transactional
    @Override
    public InviteCodeDTO getOrganizationCode(long id) {
        Member member = accessUtil.getMember()
            .orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_NOT_FOUND));
        accessUtil.isOrganizationManager(member, id)
            .orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_NOT_MANAGER));
        String redisKey = REDIS_ORG_KEY + id;
        String code = redisUtil.valueGet(redisKey);
        if (code == null || code.isEmpty()) {
            Random random = new Random();
            code = String.valueOf(random.nextInt(1000000));
            redisUtil.valueSet(redisKey, code, Duration.ofMinutes(REDIS_ORG_ALIVE_MINUTE));
        }
        return InviteCodeDTO.builder()
            .code(code)
            .build();
    }

    @Transactional
    @Override
    public Long joinOrganization(long id, InviteCodeDTO inviteCodeDTO) {
        String redisKey = REDIS_ORG_KEY + id;
        String getCode = redisUtil.valueGet(redisKey);
        if (getCode == null || getCode.isEmpty()) {
            throw new GlobalException(ErrorCode.ORGANIZATION_CODE_NOT_FOUND);
        }
        if (!getCode.equals(inviteCodeDTO.code())) {
            throw new GlobalException(ErrorCode.ORGANIZATION_CODE_NOT_MATCH);
        }
        Member member = accessUtil.getMember()
            .orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_NOT_FOUND));
        if (accessUtil.isMemberOfOrganization(member, id).isPresent()) {
            throw new GlobalException(ErrorCode.ALREADY_IN_ORGANIZATION);
        }
        Organization organization = organizationRepository.findById(id)
            .orElseThrow(() -> new GlobalException(ErrorCode.ORGANIZATION_NOT_FOUND));
        MemberOrganization memberOrganization = MemberOrganization.builder()
            .member(member)
            .organization(organization)
            .build();
        memberOrganizationRepository.save(memberOrganization);
        return organization.getId();
    }

    @Override
    public List<MemberSimpleDTO> getOrganizationMembers(long id) {
        Organization organization = organizationRepository.findById(id)
            .orElseThrow(() -> new GlobalException(ErrorCode.ORGANIZATION_NOT_FOUND));
        return organization.getMemberOrganizations()
            .stream()
            .map(MemberOrganization::getMember)
            .map(memberMapper::memberToMemberSimpleDTO)
            .toList();
    }

    @Transactional
    @Override
    public UpdateOrganizationResponse updateOrganizationName(
        long id, UpdateOrganizationRequest updateOrganizationRequest) {
        Member member = accessUtil.getMember()
            .orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_NOT_FOUND));
        Organization organization = accessUtil.isOrganizationManager(member, id)
            .orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_NOT_MANAGER));
        return organization.update(updateOrganizationRequest);
    }
}
