package com.quiz.ourclass.domain.board.dto.response;

import com.quiz.ourclass.domain.board.dto.PostListDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;

@Builder
@Schema(description = "게시글 목록 DTO")
public record PostListResponse(

    @Schema(description = "페이지", example = "1")
    Integer page,
    @Schema(description = "페이지 당 갯수", example = "3")
    Integer size,
    @Schema(description = "다음 페이지 존재 여부", example = "false")
    Boolean hasNextPage,
    @Schema(description = "게시글 목록")
    List<PostListDTO> posts
) {

}
