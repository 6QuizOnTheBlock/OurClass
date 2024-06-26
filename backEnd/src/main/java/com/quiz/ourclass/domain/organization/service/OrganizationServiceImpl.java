package com.quiz.ourclass.domain.organization.service;

import com.quiz.ourclass.domain.board.repository.PostRepository;
import com.quiz.ourclass.domain.chat.service.ChatRoomService;
import com.quiz.ourclass.domain.member.entity.Member;
import com.quiz.ourclass.domain.member.entity.Role;
import com.quiz.ourclass.domain.member.mapper.MemberMapper;
import com.quiz.ourclass.domain.organization.dto.InviteCodeDTO;
import com.quiz.ourclass.domain.organization.dto.OrganizationRequest;
import com.quiz.ourclass.domain.organization.dto.OrganizationResponse;
import com.quiz.ourclass.domain.organization.dto.request.UpdateOrganizationRequest;
import com.quiz.ourclass.domain.organization.dto.response.MemberPlayCountResponse;
import com.quiz.ourclass.domain.organization.dto.response.MemberRankPoint;
import com.quiz.ourclass.domain.organization.dto.response.OrganizationSummaryResponse;
import com.quiz.ourclass.domain.organization.dto.response.UpdateOrganizationResponse;
import com.quiz.ourclass.domain.organization.entity.MemberOrganization;
import com.quiz.ourclass.domain.organization.entity.Organization;
import com.quiz.ourclass.domain.organization.entity.Relationship;
import com.quiz.ourclass.domain.organization.mapper.OrganizationMapper;
import com.quiz.ourclass.domain.organization.repository.MemberOrganizationRepository;
import com.quiz.ourclass.domain.organization.repository.OrganizationRepository;
import com.quiz.ourclass.domain.organization.repository.RelationshipRepository;
import com.quiz.ourclass.global.dto.MemberSimpleDTO;
import com.quiz.ourclass.global.exception.ErrorCode;
import com.quiz.ourclass.global.exception.GlobalException;
import com.quiz.ourclass.global.util.ConstantUtil;
import com.quiz.ourclass.global.util.RedisUtil;
import com.quiz.ourclass.global.util.UserAccessUtil;
import java.security.SecureRandom;
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

    private final ChatRoomService chatRoomService;
    private final OrganizationRepository organizationRepository;
    private final MemberOrganizationRepository memberOrganizationRepository;
    private final PostRepository postRepository;
    private final RelationshipRepository relationshipRepository;
    private final OrganizationMapper organizationMapper;
    private final MemberMapper memberMapper;
    private final RedisUtil redisUtil;
    private final UserAccessUtil accessUtil;
    private final Random rand = new SecureRandom();

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
        Long orgId = organizationRepository.save(organization).getId();

        //단체 채팅방 생성
        chatRoomService.createChatRoom(organization);

        return orgId;
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
        if (accessUtil.isOrganizationManager(member, id).isEmpty()) {
            throw new GlobalException(ErrorCode.MEMBER_NOT_MANAGER);
        }
        String redisKey = ConstantUtil.REDIS_ORG_KEY + id;
        String code = redisUtil.valueGet(redisKey);
        if (code == null || code.isEmpty()) {
            code = String.valueOf(rand.nextInt(1000000));
            redisUtil.valueSet(redisKey, code,
                Duration.ofMinutes(ConstantUtil.REDIS_ORG_ALIVE_MINUTE));
        }
        return InviteCodeDTO.builder()
            .code(code)
            .build();
    }

    @Transactional
    @Override
    public Long joinOrganization(long id, InviteCodeDTO inviteCodeDTO) {
        String redisKey = ConstantUtil.REDIS_ORG_KEY + id;
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
        organization.plusMemberCount();
        makeRelationships(organization, member);
        return organization.getId();
    }

    private void makeRelationships(Organization organization, Member member) {
        List<MemberOrganization> orgMembers = memberOrganizationRepository.findByOrganization(
            organization);
        for (MemberOrganization orgMember : orgMembers) {
            if (orgMember.getMember().equals(member)) {
                continue;
            }
            Member member1 =
                member.getId() < orgMember.getMember().getId() ? member : orgMember.getMember();
            Member member2 =
                member.getId() > orgMember.getMember().getId() ? member : orgMember.getMember();
            Relationship relationship = Relationship.builder()
                .organization(organization)
                .member1(member1)
                .member2(member2)
                .build();
            relationshipRepository.save(relationship);
        }
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

    @Override
    public List<MemberRankPoint> getRanking(long id) {
        List<MemberOrganization> members = memberOrganizationRepository
            .findByOrganizationIdOrderByExpDesc(id);
        return members.stream()
            .map(member -> MemberRankPoint.builder()
                .name(member.getMember().getName())
                .point(member.getExp())
                .build()).toList();
    }

    @Override
    public OrganizationSummaryResponse getSummary(long id) {
        List<MemberPlayCountResponse> challengeCount = memberOrganizationRepository
            .getChallengeCountByOrganizationId(id);
        List<MemberPlayCountResponse> relayCount = memberOrganizationRepository
            .getRelayCountByOrganizationId(id);
        List<MemberPlayCountResponse> postCount = postRepository.countPostsByOrganizationIdGroupByMember(
            id);
        return OrganizationSummaryResponse.builder()
            .challengeCounts(challengeCount)
            .relayCounts(relayCount)
            .postCounts(postCount)
            .build();
    }
}
