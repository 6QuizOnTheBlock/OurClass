package com.quiz.ourclass.domain.organization.service;

import static com.quiz.ourclass.global.util.ConstantUtil.HOME_FRIEND_COUNT;

import com.quiz.ourclass.domain.board.repository.PostRepository;
import com.quiz.ourclass.domain.challenge.repository.ChallengeRepository;
import com.quiz.ourclass.domain.member.entity.Member;
import com.quiz.ourclass.domain.member.mapper.MemberMapper;
import com.quiz.ourclass.domain.organization.dto.request.RelationRequest;
import com.quiz.ourclass.domain.organization.dto.request.TagGreetingRequest;
import com.quiz.ourclass.domain.organization.dto.request.UpdateExpRequest;
import com.quiz.ourclass.domain.organization.dto.response.MemberDetailResponse;
import com.quiz.ourclass.domain.organization.dto.response.OrganizationHomeResponse;
import com.quiz.ourclass.domain.organization.dto.response.RelationResponse;
import com.quiz.ourclass.domain.organization.dto.response.RelationSimpleResponse;
import com.quiz.ourclass.domain.organization.dto.response.UpdateExpResponse;
import com.quiz.ourclass.domain.organization.entity.MemberOrganization;
import com.quiz.ourclass.domain.organization.entity.Organization;
import com.quiz.ourclass.domain.organization.entity.Relationship;
import com.quiz.ourclass.domain.organization.entity.TagGreeting;
import com.quiz.ourclass.domain.organization.mapper.MemberOrganizationMapper;
import com.quiz.ourclass.domain.organization.repository.MemberOrganizationRepository;
import com.quiz.ourclass.domain.organization.repository.RelationshipRepository;
import com.quiz.ourclass.domain.organization.repository.TagGreetingRepository;
import com.quiz.ourclass.domain.relay.repository.RelayMemberRepository;
import com.quiz.ourclass.domain.relay.repository.RelayRepository;
import com.quiz.ourclass.global.dto.MemberSimpleDTO;
import com.quiz.ourclass.global.exception.ErrorCode;
import com.quiz.ourclass.global.exception.GlobalException;
import com.quiz.ourclass.global.util.UserAccessUtil;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Slf4j
@Service
public class MemberOrgServiceImpl implements MemberOrgService {

    private final MemberOrganizationRepository memberOrganizationRepository;
    private final RelationshipRepository relationshipRepository;
    private final UserAccessUtil accessUtil;
    private final RelayMemberRepository relayMemberRepository;
    private final ChallengeRepository challengeRepository;
    private final RelayRepository relayRepository;
    private final PostRepository postRepository;
    private final TagGreetingRepository tagGreetingRepository;
    private final MemberMapper memberMapper;
    private final MemberOrganizationMapper memberOrganizationMapper;

    @Transactional
    @Override
    public UpdateExpResponse updateMemberExp(long id, UpdateExpRequest updateExpRequest) {
        Member member = accessUtil.getMember().orElseThrow(
            () -> new GlobalException(ErrorCode.MEMBER_NOT_FOUND));
        if (accessUtil.isOrganizationManager(member, id).isEmpty()) {
            throw new GlobalException(ErrorCode.MEMBER_NOT_MANAGER);
        }
        MemberOrganization memberOrganization = memberOrganizationRepository.findByMemberIdAndOrganizationId(
                updateExpRequest.memberId(), id)
            .orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_ORGANIZATION_NOT_FOUND));
        return memberOrganization.updateExp(updateExpRequest.exp());
    }

    @Override
    public RelationResponse getMemberRelation(long id, RelationRequest relationRequest) {
        long member1Id = Math.min(relationRequest.sourceMemberId(),
            relationRequest.targetMemberId());
        long member2Id = Math.max(relationRequest.sourceMemberId(),
            relationRequest.targetMemberId());
        Relationship relationship = relationshipRepository.findByOrganizationIdAndMember1IdAndMember2Id(
                id, member1Id, member2Id)
            .orElseThrow(() -> new GlobalException(ErrorCode.RELATION_NOT_FOUND));
        Member targetMember =
            relationship.getMember1().getId() == relationRequest.targetMemberId() ?
                relationship.getMember1() : relationship.getMember2();
        int receiveCount = relayMemberRepository.countByCurMemberIdAndNextMemberId(
            relationRequest.targetMemberId(), relationRequest.sourceMemberId());
        int sendCount = relayMemberRepository.countByCurMemberIdAndNextMemberId(
            relationRequest.sourceMemberId(), relationRequest.targetMemberId());
        return RelationResponse.builder()
            .memberId(targetMember.getId())
            .memberName(targetMember.getName())
            .relationPoint((int) relationship.getRelationPoint())
            .groupCount(relationship.getDesignGroupCount() + relationship.getFreeGroupCount())
            .tagGreetingCount(relationship.getTagGreetingCount())
            .receiveCount(receiveCount)
            .sendCount(sendCount).build();
    }

    @Override
    public MemberDetailResponse getMemberDetail(long id, long memberId) {
        MemberOrganization memberOrganization = memberOrganizationRepository
            .findByMemberIdAndOrganizationId(memberId, id)
            .orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_ORGANIZATION_NOT_FOUND));
        Member member = memberOrganization.getMember();
        Organization organization = memberOrganization.getOrganization();
        long challengeCount = challengeRepository.countParticipateChallenge(member, organization);
        long relayCount = relayRepository.countParticipateRelay(member, organization);
        long postCount = postRepository.countByAuthorAndOrganization(member, organization);
        return MemberDetailResponse.builder()
            .name(member.getName())
            .photo(member.getProfileImage())
            .isolationPoint(memberOrganization.getIsolationPoint())
            .exp(memberOrganization.getExp())
            .challengeCount(challengeCount)
            .relayCount(relayCount)
            .postCount(postCount)
            .build();
    }

    @Override
    public List<RelationSimpleResponse> getMemberRelations(long id, long memberId, Long limit) {
        return getFriendlyResponse(id, memberId, limit);
    }

    @Override
    public OrganizationHomeResponse getOrganizationHome(long id) {
        Member member = accessUtil.getMember().orElseThrow();
        MemberOrganization memberOrganization = accessUtil.isMemberOfOrganization(member, id)
            .orElseThrow();
        List<RelationSimpleResponse> friendlyResponse = getFriendlyResponse(id, member.getId(),
            HOME_FRIEND_COUNT);
        return memberOrganizationMapper.memberOrgToOrganizationHome(memberOrganization,
            friendlyResponse);
    }

    @Transactional
    @Override
    public int tagGreeting(TagGreetingRequest tagGreetingRequest) {
        Member loginMember = accessUtil.getMember()
            .orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_NOT_FOUND));
        long member1 = Math.min(loginMember.getId(), tagGreetingRequest.memberId());
        long member2 = Math.max(loginMember.getId(), tagGreetingRequest.memberId());
        Relationship relationship = relationshipRepository.findByOrganizationIdAndMember1IdAndMember2Id(
                tagGreetingRequest.organizationId(), member1, member2)
            .orElseThrow(() -> new GlobalException(ErrorCode.RELATION_NOT_FOUND));
        TagGreeting tagGreeting = TagGreeting.builder()
            .relationship(relationship).date(LocalDateTime.now()).build();
        tagGreetingRepository.save(tagGreeting);
        int tagGreetingCount = relationship.updateTagGreetingCount();
        return tagGreetingCount;
    }

    private List<RelationSimpleResponse> getFriendlyResponse(long organizationId, long memberId,
        Long limit) {
        List<Relationship> relations = relationshipRepository.getMemberRelations(
            organizationId, memberId, limit);
        return relations.stream()
            .map(relationship -> {
                Member target = relationship.getMember1().getId() == memberId
                    ? relationship.getMember2() : relationship.getMember1();
                MemberSimpleDTO memberSimpleDTO = memberMapper.memberToMemberSimpleDTO(target);
                return RelationSimpleResponse.builder()
                    .member(memberSimpleDTO)
                    .relationPoint((int) relationship.getRelationPoint())
                    .build();
            }).toList();
    }
}
