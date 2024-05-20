package com.quiz.ourclass.domain.challenge.dto.response;

import com.quiz.ourclass.global.dto.MemberSimpleDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;

@Schema(description = "그룹 자동 매칭 응답 DTO")
@Builder
public record AutoGroupMatchingResponse(
    @Schema(description = "그룹 멤버 목록")
    List<MemberSimpleDTO> members,
    @Schema(description = "그룹 참여인원 수")
    int headCount
) {

}
