package com.quiz.ourclass.domain.organization.service;

import com.quiz.ourclass.domain.member.entity.Member;
import com.quiz.ourclass.domain.organization.dto.request.RelationRequest;
import com.quiz.ourclass.domain.organization.dto.request.UpdateExpRequest;
import com.quiz.ourclass.domain.organization.dto.response.RelationResponse;
import com.quiz.ourclass.domain.organization.dto.response.UpdateExpResponse;
import com.quiz.ourclass.domain.organization.entity.MemberOrganization;
import com.quiz.ourclass.domain.organization.entity.Relationship;
import com.quiz.ourclass.domain.organization.repository.MemberOrganizationRepository;
import com.quiz.ourclass.domain.organization.repository.RelationshipRepository;
import com.quiz.ourclass.domain.relay.repository.RelayMemberRepository;
import com.quiz.ourclass.global.exception.ErrorCode;
import com.quiz.ourclass.global.exception.GlobalException;
import com.quiz.ourclass.global.util.UserAccessUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberOrgServiceImpl implements MemberOrgService {

    private final MemberOrganizationRepository memberOrganizationRepository;
    private final RelationshipRepository relationshipRepository;
    private final UserAccessUtil accessUtil;
    private final RelayMemberRepository relayMemberRepository;

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
        return memberOrganization.updateExp(updateExpRequest);
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
            .relationPoint(relationship.getRelationPoint())
            .groupCount(relationship.getGroupCount())
            .tagCount(relationship.getTagCount())
            .receiveCount(receiveCount)
            .sendCount(sendCount).build();
    }
}
