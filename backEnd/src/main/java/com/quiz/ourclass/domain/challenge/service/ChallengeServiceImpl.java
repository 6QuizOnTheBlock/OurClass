package com.quiz.ourclass.domain.challenge.service;

import com.quiz.ourclass.domain.challenge.dto.ChallengSliceRequest;
import com.quiz.ourclass.domain.challenge.dto.ChallengeRequest;
import com.quiz.ourclass.domain.challenge.dto.ChallengeSliceResponse;
import com.quiz.ourclass.domain.challenge.entity.Challenge;
import com.quiz.ourclass.domain.challenge.entity.ChallengeGroup;
import com.quiz.ourclass.domain.challenge.entity.GroupMember;
import com.quiz.ourclass.domain.challenge.mapper.ChallengeGroupMapper;
import com.quiz.ourclass.domain.challenge.mapper.ChallengeMapper;
import com.quiz.ourclass.domain.challenge.repository.ChallengeGroupRepository;
import com.quiz.ourclass.domain.challenge.repository.ChallengeRepository;
import com.quiz.ourclass.domain.challenge.repository.GroupMemberRepository;
import com.quiz.ourclass.domain.member.entity.Member;
import com.quiz.ourclass.domain.member.repository.MemberRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ChallengeServiceImpl implements ChallengeService {

    private final ChallengeRepository challengeRepository;
    private final ChallengeGroupRepository challengeGroupRepository;
    private final MemberRepository memberRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final ChallengeMapper challengeMapper;
    private final ChallengeGroupMapper challengeGroupMapper;

    @Override
    public ChallengeSliceResponse getChallenges(ChallengSliceRequest challengSliceRequest) {
        return challengeRepository.getChallenges(challengSliceRequest);
    }

    @Transactional
    @Override
    public long createChallenge(ChallengeRequest challengeRequest) {
        Challenge challenge = challengeMapper.challengeRequestToChallenge(challengeRequest);
        challengeRepository.save(challenge);
        //TODO: org도메인 구현 뒤에 매핑 필요
        if (!challengeRequest.groups().isEmpty()) {
            challengeRequest.groups().forEach(request -> {
                ChallengeGroup group = challengeGroupMapper.groupMatchingRequestToChallengeGroup(
                    request);
                group.setChallenge(challenge);
                challengeGroupRepository.save(group);
                List<Member> members = request.students().stream()
                    .map(memberRepository::findById).filter(Optional::isPresent).map(Optional::get)
                    .toList();
                List<GroupMember> groupMembers = members.stream().map(
                        member -> GroupMember.builder().member(member).challengeGroup(group).build())
                    .toList();
                groupMemberRepository.saveAll(groupMembers);
            });
        }
        return challenge.getId();
    }
}
