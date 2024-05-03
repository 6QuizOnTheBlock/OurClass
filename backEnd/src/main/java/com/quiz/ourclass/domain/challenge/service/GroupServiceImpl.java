package com.quiz.ourclass.domain.challenge.service;

import static com.quiz.ourclass.global.exception.ErrorCode.CHALLENGE_NOT_FOUND;
import static com.quiz.ourclass.global.exception.ErrorCode.PERMISSION_DENIED;

import com.quiz.ourclass.domain.challenge.entity.Challenge;
import com.quiz.ourclass.domain.challenge.entity.ChallengeGroup;
import com.quiz.ourclass.domain.challenge.entity.GroupMember;
import com.quiz.ourclass.domain.challenge.entity.GroupType;
import com.quiz.ourclass.domain.challenge.repository.ChallengeGroupRepository;
import com.quiz.ourclass.domain.challenge.repository.ChallengeRepository;
import com.quiz.ourclass.domain.challenge.repository.GroupMemberRepository;
import com.quiz.ourclass.domain.member.repository.MemberRepository;
import com.quiz.ourclass.domain.notice.dto.SseDTO;
import com.quiz.ourclass.domain.notice.dto.SseType;
import com.quiz.ourclass.domain.notice.service.SseService;
import com.quiz.ourclass.global.exception.GlobalException;
import com.quiz.ourclass.global.util.RedisUtil;
import com.quiz.ourclass.global.util.UserAccessUtil;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class GroupServiceImpl implements GroupService {

    private final SseService sseService;
    private final ChallengeGroupRepository challengeGroupRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final MemberRepository memberRepository;
    private final ChallengeRepository challengeRepository;
    private final UserAccessUtil accessUtil;
    private final RedisUtil redisUtil;
    private final static String REDIS_GROUP_KEY = "CHALLENGE_LEADER:";

    @Transactional
    @Override
    public String createMatchingRoom(long challengeId) {
        long MemberId = accessUtil.getMember().getId();
        String dataKey = makeGroupKey(challengeId, MemberId);
        redisUtil.setAdd(dataKey, String.valueOf(MemberId));
        return dataKey;
    }

    @Transactional
    @Override
    public boolean joinMatchingRoom(String key, boolean joinStatus) {
        long memberId = accessUtil.getMember().getId();
        long leaderId = getLeaderIdFromKey(key);
        SseDTO sseDTO = SseDTO.builder()
            .eventType(SseType.INVITE_RESPONSE)
            .receiverId(leaderId)
            .url(key)
            .data(String.valueOf(joinStatus))
            .time(LocalDateTime.now())
            .build();
        sseService.send(sseDTO);
        if (!joinStatus) {
            return false;
        }
        redisUtil.setAdd(key, String.valueOf(memberId));
        return true;
    }

    @Transactional
    @Override
    public long createGroup(String key) {
        Set<String> members = redisUtil.setMembers(key);
        Challenge challenge = challengeRepository.findById(getChallengeIdFromKey(key))
            .orElseThrow(() -> new GlobalException(CHALLENGE_NOT_FOUND));
        long leaderId = getLeaderIdFromKey(key);
        ChallengeGroup group = ChallengeGroup.builder()
            .challenge(challenge)
            .leaderId(leaderId)
            .groupType(GroupType.FREE)
            .headCount(members.size())
            .completeStatus(false)
            .createTime(LocalDateTime.now())
            .build();
        challengeGroupRepository.save(group);
        List<GroupMember> groupMembers = members.stream().map(Long::parseLong)
            .map(memberRepository::findById)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(member -> GroupMember.builder().member(member).challengeGroup(group).build())
            .toList();
        groupMembers.forEach(member -> {
            SseDTO sseDTO = SseDTO.builder()
                .eventType(SseType.CREATE_GROUP)
                .receiverId(member.getId())
                .url(key)
                .time(LocalDateTime.now())
                .build();
            sseService.send(sseDTO);
        });
        groupMemberRepository.saveAll(groupMembers);
        redisUtil.delete(key);
        return group.getId();
    }

    @Transactional
    @Override
    public void deleteMatchingMember(String key, Long memberId) {
        long loginUserId = accessUtil.getMember().getId();
        if (getLeaderIdFromKey(key) != loginUserId) {
            throw new GlobalException(PERMISSION_DENIED);
        }
        SseDTO sseDTO = SseDTO.builder()
            .eventType(SseType.KICK_MEMBER)
            .receiverId(memberId)
            .url(key)
            .time(LocalDateTime.now())
            .build();
        sseService.send(sseDTO);
        redisUtil.removeMembers(key, String.valueOf(memberId));
    }

    @Override
    public void inviteMatchingRoom(String key, Long memberId) {
        SseDTO sseDTO = SseDTO.builder()
            .eventType(SseType.INVITE_REQUEST)
            .receiverId(memberId)
            .url(key)
            .time(LocalDateTime.now())
            .build();
        sseService.send(sseDTO);
    }

    private static String makeGroupKey(long challengeId, long MemberId) {
        return REDIS_GROUP_KEY + challengeId + "_" + MemberId;
    }

    private static long getChallengeIdFromKey(String key) {
        return Long.parseLong(key.split(":")[1].split("_")[0]);
    }

    private static long getLeaderIdFromKey(String key) {
        return Long.parseLong(key.split(":")[1].split("_")[1]);
    }
}
