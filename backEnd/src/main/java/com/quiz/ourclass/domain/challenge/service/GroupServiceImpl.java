package com.quiz.ourclass.domain.challenge.service;

import static com.quiz.ourclass.global.exception.ErrorCode.CHALLENGE_NOT_FOUND;
import static com.quiz.ourclass.global.exception.ErrorCode.PERMISSION_DENIED;

import com.quiz.ourclass.domain.challenge.dto.request.AutoGroupMatchingRequest;
import com.quiz.ourclass.domain.challenge.dto.response.AutoGroupMatchingResponse;
import com.quiz.ourclass.domain.challenge.dto.response.MatchingRoomResponse;
import com.quiz.ourclass.domain.challenge.entity.Challenge;
import com.quiz.ourclass.domain.challenge.entity.ChallengeGroup;
import com.quiz.ourclass.domain.challenge.entity.GroupMember;
import com.quiz.ourclass.domain.challenge.entity.GroupType;
import com.quiz.ourclass.domain.challenge.repository.ChallengeGroupRepository;
import com.quiz.ourclass.domain.challenge.repository.ChallengeRepository;
import com.quiz.ourclass.domain.challenge.repository.GroupMemberRepository;
import com.quiz.ourclass.domain.member.mapper.MemberMapper;
import com.quiz.ourclass.domain.member.repository.MemberRepository;
import com.quiz.ourclass.domain.notice.dto.SseDTO;
import com.quiz.ourclass.domain.notice.dto.SseType;
import com.quiz.ourclass.domain.notice.service.SseService;
import com.quiz.ourclass.domain.organization.entity.Relationship;
import com.quiz.ourclass.domain.organization.repository.OrganizationRepository;
import com.quiz.ourclass.domain.organization.repository.RelationshipRepository;
import com.quiz.ourclass.global.dto.MemberSimpleDTO;
import com.quiz.ourclass.global.exception.ErrorCode;
import com.quiz.ourclass.global.exception.GlobalException;
import com.quiz.ourclass.global.util.RedisUtil;
import com.quiz.ourclass.global.util.UserAccessUtil;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
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
    private final RelationshipRepository relationshipRepository;
    private final OrganizationRepository organizationRepository;
    private final UserAccessUtil accessUtil;
    private final RedisUtil redisUtil;
    private final MemberMapper memberMapper;
    private final static String REDIS_GROUP_KEY = "CHALLENGE_LEADER:";

    @Transactional
    @Override
    public MatchingRoomResponse createMatchingRoom(long challengeId) {
        long MemberId = accessUtil.getMember()
            .orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_NOT_FOUND)).getId();
        String dataKey = makeGroupKey(challengeId, MemberId);
        Set<String> redisMembers = redisUtil.setMembers(dataKey);
        if (redisMembers != null && !redisMembers.isEmpty()) {
            redisUtil.delete(dataKey);
        }
        redisUtil.setAdd(dataKey, String.valueOf(MemberId));
        Challenge challenge = challengeRepository.findById(challengeId)
            .orElseThrow(() -> new GlobalException(CHALLENGE_NOT_FOUND));
        int minCount = challenge.getMinCount();
        return MatchingRoomResponse.builder()
            .dataKey(dataKey)
            .minCount(minCount)
            .build();
    }

    @Transactional
    @Override
    public boolean joinMatchingRoom(String key, boolean joinStatus) {
        long memberId = accessUtil.getMember()
            .orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_NOT_FOUND)).getId();
        long leaderId = getLeaderIdFromKey(key);
        SseDTO sseDTO = SseDTO.builder()
            .eventType(SseType.INVITE_RESPONSE)
            .receiverId(leaderId)
            .url(String.valueOf(memberId))
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
        if (members.size() < challenge.getMinCount()) {
            throw new GlobalException(ErrorCode.NOT_ENOUGH_GROUP_MEMBER);
        }
        long leaderId = getLeaderIdFromKey(key);
        LocalDateTime createTime = LocalDateTime.now();
        ChallengeGroup group = ChallengeGroup.builder()
            .challenge(challenge)
            .leaderId(leaderId)
            .groupType(GroupType.FREE)
            .headCount(members.size())
            .completeStatus(false)
            .createTime(createTime)
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
                .receiverId(member.getMember().getId())
                .time(createTime)
                .build();
            sseService.send(sseDTO);
        });
        groupMemberRepository.saveAll(groupMembers);
        challenge.updateHeadCount(groupMembers.size());
        updateGroupCount(members, challenge.getOrganization().getId(), group.getGroupType());
        redisUtil.delete(key);
        return group.getId();
    }

    private void updateGroupCount(Set<String> members, long orgId, GroupType groupType) {
        List<Long> memberIds = members.stream().map(Long::parseLong).sorted().toList();
        for (int i = 0; i < memberIds.size() - 1; i++) {
            for (int j = i + 1; j < memberIds.size(); j++) {
                Relationship relationship = relationshipRepository.findByOrganizationIdAndMember1IdAndMember2Id(
                        orgId, memberIds.get(i), memberIds.get(j))
                    .orElseThrow(() -> new GlobalException(ErrorCode.RELATION_NOT_FOUND));
                if (groupType.equals(GroupType.FREE)) {
                    relationship.updateFreeGroupCount();
                } else {
                    relationship.updateDesignGroupCount();
                }
            }
        }
    }


    @Transactional
    @Override
    public void deleteMatchingMember(String key, Long memberId) {
        long loginUserId = accessUtil.getMember()
            .orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_NOT_FOUND)).getId();
        if (getLeaderIdFromKey(key) != loginUserId) {
            throw new GlobalException(PERMISSION_DENIED);
        }
        SseDTO sseDTO = SseDTO.builder()
            .eventType(SseType.KICK_MEMBER)
            .receiverId(memberId)
            .time(LocalDateTime.now())
            .build();
        sseService.send(sseDTO);
        redisUtil.removeMembers(key, String.valueOf(memberId));
    }

    @Override
    public void inviteMatchingRoom(String key, Long memberId) {
        Long loginMemberId = accessUtil.getMember().orElseThrow().getId();
        SseDTO sseDTO = SseDTO.builder()
            .eventType(SseType.INVITE_REQUEST)
            .receiverId(memberId)
            .url(String.valueOf(loginMemberId))
            .data(key)
            .time(LocalDateTime.now())
            .build();
        sseService.send(sseDTO);
    }

    @Override
    public List<AutoGroupMatchingResponse> getGroupMatching(
        AutoGroupMatchingRequest autoGroupMatchingRequest) {
        if (autoGroupMatchingRequest.minCount() > autoGroupMatchingRequest.members().size()) {
            throw new GlobalException(ErrorCode.GROUP_MIN_COUNT_OVER);
        }
        switch (autoGroupMatchingRequest.matchingType()) {
            case RAND -> {
                return getRandomGroup(autoGroupMatchingRequest);
            }
            case FRIENDLY -> {
                return getFriendlyGroup(autoGroupMatchingRequest);
            }
            case UNFRIENDLY -> {
                return getUnfriendlyGroup(autoGroupMatchingRequest);
            }
        }
        return null;
    }

    private List<AutoGroupMatchingResponse> getRandomGroup(
        AutoGroupMatchingRequest autoGroupMatchingRequest) {
        List<MemberSimpleDTO> members = autoGroupMatchingRequest.members().stream()
            .map(memberRepository::findById)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(memberMapper::memberToMemberSimpleDTO)
            .collect(Collectors.toList());
        Collections.shuffle(members);
        List<List<MemberSimpleDTO>> groups = new ArrayList<>();
        int groupSize = autoGroupMatchingRequest.minCount();
        int groupCount = members.size() / groupSize;
        int left = members.size() % groupSize;
        int addCount = left / groupCount;
        int start = 0;
        for (int i = 0; i < groupCount; i++) {
            int size = groupSize;
            if (i < left) {
                if (left >= addCount) {
                    size += left;
                } else {
                    size += addCount;
                    left -= addCount;
                }
            }
            groups.add(members.subList(start, start + size));
            start += size;
        }
        return groups.stream().map(group -> AutoGroupMatchingResponse.builder()
                .members(group)
                .headCount(group.size())
                .build())
            .toList();
    }

    private List<AutoGroupMatchingResponse> getFriendlyGroup(
        AutoGroupMatchingRequest autoGroupMatchingRequest) {
        return null;
    }

    private List<AutoGroupMatchingResponse> getUnfriendlyGroup(
        AutoGroupMatchingRequest autoGroupMatchingRequest) {
        return null;
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
