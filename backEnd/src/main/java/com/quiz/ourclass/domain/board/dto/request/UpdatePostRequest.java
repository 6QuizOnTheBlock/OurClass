package com.quiz.ourclass.domain.board.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record UpdatePostRequest(
    @Schema(description = "게시글 제목", example = "ㅈㄱㄴ")
    String title,
    @Schema(description = "게시글 내용", example = "오늘 나랑 PC방 갈 사람?")
    String content,
    @Schema(description = "익명 여부", example = "true")
    Boolean secretStatus,
    @Schema(description = "이미지 삭제 여부", example = "false")
    Boolean imageDelete
) {

}
