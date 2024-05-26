package com.quiz.ourclass.domain.challenge.mapper;

import com.quiz.ourclass.domain.challenge.dto.ChallengeSimpleDTO;
import com.quiz.ourclass.domain.challenge.dto.request.ChallengeRequest;
import com.quiz.ourclass.domain.challenge.dto.response.ChallengeSimpleResponse;
import com.quiz.ourclass.domain.challenge.dto.response.RunningMemberChallengeResponse;
import com.quiz.ourclass.domain.challenge.entity.Challenge;
import com.quiz.ourclass.domain.challenge.entity.ChallengeGroup;
import com.quiz.ourclass.domain.challenge.entity.GroupMember;
import com.quiz.ourclass.global.dto.MemberSimpleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ChallengeMapper {

    Challenge challengeRequestToChallenge(ChallengeRequest challengeRequest);

    ChallengeSimpleDTO challengeToChallengeSimpleDTO(Challenge challenge);

    ChallengeSimpleResponse challengeToChallengeSimpleResponse(Challenge challenge);

    @Mapping(target = "type", source = "challengeGroup.groupType")
    @Mapping(target = "createTime", source = "challengeGroup.createTime")
    @Mapping(target = "endStatus", source = "challengeGroup.completeStatus")
    @Mapping(target = "memberNames", source = "challengeGroup.groupMembers")
    RunningMemberChallengeResponse groupToRunningMember(ChallengeSimpleDTO challengeSimpleDTO,
        ChallengeGroup challengeGroup, boolean leaderStatus);

    @Mapping(target = "id", source = "member.id")
    @Mapping(target = "name", source = "member.name")
    @Mapping(target = "photo", source = "member.profileImage")
    MemberSimpleDTO groupMemberToMemberSimpleDTO(GroupMember groupMember);
}
