package com.quiz.ourclass.domain.board.dto;

import com.quiz.ourclass.domain.board.entity.PostCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class PostRequest {

    @Schema(description = "게시글 카테고리", example = "FREE")
    private PostCategory type;
    @Schema(description = "게시글 제목", example = "ㅈㄱㄴ")
    private String title;
    @Schema(description = "게시글 내용", example = "오늘 나랑 PC방 갈 사람?")
    private String content;
    @Schema(description = "익명 여부", example = "true")
    private Boolean anonymous;
}
