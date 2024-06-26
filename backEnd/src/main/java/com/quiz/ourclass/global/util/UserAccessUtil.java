package com.quiz.ourclass.global.util;

import com.quiz.ourclass.domain.member.entity.Member;
import com.quiz.ourclass.domain.member.repository.MemberRepository;
import com.quiz.ourclass.domain.organization.entity.MemberOrganization;
import com.quiz.ourclass.domain.organization.entity.Organization;
import com.quiz.ourclass.domain.organization.repository.MemberOrganizationRepository;
import com.quiz.ourclass.domain.organization.repository.OrganizationRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserAccessUtil {

    private final MemberRepository memberRepository;
    private final MemberOrganizationRepository memberOrganizationRepository;
    private final OrganizationRepository organizationRepository;

    //유저 정보 가져오기
    public Optional<Member> getMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        return memberRepository.findByName(userName);
    }

    //유저가 단체에 속해있는지 유효성 검사
    public Optional<MemberOrganization> isMemberOfOrganization(Member member, Long orgId) {
        return memberOrganizationRepository.findByMemberIdAndOrganizationId(member.getId(), orgId);
    }

    public Optional<Organization> isOrganizationManager(Member member, Long orgId) {
        return organizationRepository.findByIdAndManager(orgId, member);
    }
}
