package com.quiz.ourclass.global.util;

import com.quiz.ourclass.domain.member.entity.Member;
import com.quiz.ourclass.domain.member.repository.MemberRepository;
import com.quiz.ourclass.domain.organization.entity.MemberOrganization;
import com.quiz.ourclass.domain.organization.repository.MemberOrganizationRepository;
import com.quiz.ourclass.global.exception.ErrorCode;
import com.quiz.ourclass.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserAccessUtil {

    private final MemberRepository memberRepository;
    private final MemberOrganizationRepository memberOrganizationRepository;

    public Member getMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        return memberRepository.findByName(userName)
            .orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_NOT_FOUND));
    }

    public MemberOrganization isMemberOfOrganization(Member member, Long orgId) {
        return memberOrganizationRepository.findByMemberIdAndOrganizationId(member.getId(), orgId)
            .orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_NOT_IN_ORGANIZATION));
    }
}
