package com.quiz.ourclass.domain.challenge.mapper;

import com.quiz.ourclass.domain.challenge.dto.ChallengeSimpleDTO;
import com.quiz.ourclass.domain.challenge.dto.request.ChallengeRequest;
import com.quiz.ourclass.domain.challenge.dto.response.ChallengeSimpleResponse;
import com.quiz.ourclass.domain.challenge.dto.response.RunningMemberChallengeResponse;
import com.quiz.ourclass.domain.challenge.entity.Challenge;
import com.quiz.ourclass.domain.challenge.entity.ChallengeGroup;
import com.quiz.ourclass.domain.challenge.entity.GroupMember;
import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ChallengeMapper {

    Challenge challengeRequestToChallenge(ChallengeRequest challengeRequest);

    ChallengeSimpleDTO challengeToChallengeSimpleDTO(Challenge challenge);

    ChallengeSimpleResponse challengeToChallengeSimpleResponse(Challenge challenge);

    @Mapping(target = "type", source = "challengeGroup.groupType")
    @Mapping(target = "createTime", source = "challengeGroup.createTime")
    @Mapping(target = "endStatus", source = "challengeGroup.completeStatus")
    @Mapping(target = "memberNames", source = "challengeGroup.groupMembers", qualifiedByName = "MemberToString")
    RunningMemberChallengeResponse groupToRunningMember(ChallengeSimpleDTO challengeSimpleDTO,
        ChallengeGroup challengeGroup, boolean leaderStatus);

    @Named("MemberToString")
    static List<String> memberToString(List<GroupMember> groupMembers) {
        return groupMembers.stream()
            .map(groupMember -> groupMember.getMember().getName())
            .collect(Collectors.toList());
    }
}
