package com.quiz.ourclass.domain.challenge.dto.response;

import com.quiz.ourclass.domain.challenge.dto.ChallengeSimpleDTO;
import com.quiz.ourclass.domain.challenge.entity.GroupType;
import com.quiz.ourclass.global.dto.MemberSimpleDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

@Schema(description = "학생용 진행중인 함께달리기 조회 응답 DTO")
@Builder
public record RunningMemberChallengeResponse(
    ChallengeSimpleDTO challengeSimpleDTO,
    @Schema(description = "그룹 리더 여부")
    Boolean leaderStatus,
    @Schema(description = "그룹 구성원 목록")
    List<MemberSimpleDTO> memberNames,
    @Schema(description = "그룹 타입 (지정/자율)")
    GroupType type,
    @Schema(description = "그룹 생성 시간 (블루투스 체킹 기준)")
    LocalDateTime createTime,
    @Schema(description = "레포트 제출 완료 여부")
    Boolean endStatus
) {

}
