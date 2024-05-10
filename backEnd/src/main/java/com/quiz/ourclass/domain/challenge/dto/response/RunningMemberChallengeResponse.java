package com.quiz.ourclass.domain.challenge.dto.response;

import com.quiz.ourclass.domain.challenge.dto.ChallengeSimpleDTO;
import com.quiz.ourclass.domain.challenge.entity.GroupType;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

@Builder
public record RunningMemberChallengeResponse(
    ChallengeSimpleDTO challengeSimpleDTO,
    Boolean leaderStatus,
    List<String> memberNames,
    GroupType type,
    LocalDateTime createTime,
    Boolean endStatus
) {

}
