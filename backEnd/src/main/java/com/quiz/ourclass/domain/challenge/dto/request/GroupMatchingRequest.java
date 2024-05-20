package com.quiz.ourclass.domain.challenge.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "그룹 매칭 요청 DTO")
public record GroupMatchingRequest(
    @Schema(description = "그룹 참여자 수")
    int headCount,
    @Schema(description = "그룹장 id")
    long leaderId,
    @Schema(description = "그룹 구성원")
    List<Long> students
) {

}
