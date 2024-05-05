package com.quiz.ourclass.domain.organization.service;

import com.quiz.ourclass.domain.member.entity.Member;
import com.quiz.ourclass.domain.member.entity.Role;
import com.quiz.ourclass.domain.organization.dto.InviteCodeDTO;
import com.quiz.ourclass.domain.organization.dto.OrganizationRequest;
import com.quiz.ourclass.domain.organization.dto.OrganizationResponse;
import com.quiz.ourclass.domain.organization.entity.MemberOrganization;
import com.quiz.ourclass.domain.organization.entity.Organization;
import com.quiz.ourclass.domain.organization.mapper.OrganizationMapper;
import com.quiz.ourclass.domain.organization.repository.MemberOrganizationRepository;
import com.quiz.ourclass.domain.organization.repository.OrganizationRepository;
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

@RequiredArgsConstructor
@Service
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final MemberOrganizationRepository memberOrganizationRepository;
    private final OrganizationMapper organizationMapper;
    private final RedisUtil redisUtil;
    private final UserAccessUtil accessUtil;
    private final static String REDIS_ORG_KEY = "ORGANIZATION:";
    private final static int REDIS_ORG_ALIVE_MINUTE = 10;

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
                .toList();
        }
        List<MemberOrganization> memberOrganizations = memberOrganizationRepository.findByMember(
            member);
        return memberOrganizations.stream()
            .map(MemberOrganization::getOrganization)
            .map(organizationMapper::organizationToOrganizationResponse)
            .toList();
    }

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
}
