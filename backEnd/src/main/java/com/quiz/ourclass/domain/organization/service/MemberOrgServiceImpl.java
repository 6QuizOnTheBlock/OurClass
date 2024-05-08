package com.quiz.ourclass.domain.organization.service;

import com.quiz.ourclass.domain.member.entity.Member;
import com.quiz.ourclass.domain.organization.dto.request.UpdateExpRequest;
import com.quiz.ourclass.domain.organization.dto.response.UpdateExpResponse;
import com.quiz.ourclass.domain.organization.entity.MemberOrganization;
import com.quiz.ourclass.domain.organization.repository.MemberOrganizationRepository;
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
    private final UserAccessUtil accessUtil;

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
}
