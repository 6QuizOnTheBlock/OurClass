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

    //유저 정보 가져오기
    public Member getMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        return memberRepository.findByName(userName)
            .orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_NOT_FOUND));
    }

    //유저가 단체에 속해있는지 유효성 검사
    public MemberOrganization isMemberOfOrganization(Member member, Long orgId) {
        return memberOrganizationRepository.findByMemberIdAndOrganizationId(member.getId(), orgId)
            .orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_NOT_IN_ORGANIZATION));
    }
}
