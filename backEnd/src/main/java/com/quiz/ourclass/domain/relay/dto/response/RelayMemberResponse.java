package com.quiz.ourclass.domain.relay.dto.response;

import com.quiz.ourclass.global.dto.MemberSimpleDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "릴레이 참여 상세 주자 응답 DTO")
public record RelayMemberResponse(
    @Schema(description = "릴레이 멤버 id")
    long id,
    @Schema(description = "릴레이 순서")
    int turn,
    @Schema(description = "주자 정보")
    MemberSimpleDTO member,
    @Schema(description = "받은 시간")
    LocalDateTime time,
    @Schema(description = "받은 질문")
    String question,
    @Schema(description = "종료 여부")
    boolean endStatus
) {

}
