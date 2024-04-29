package com.quiz.ourclass.domain.board.dto.response;


import com.quiz.ourclass.domain.board.dto.CommentDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "게시글 상세 정보 반환 DTO")
public record PostDetailResponse(
    @Schema(description = "게시글 작성자", example = "오하빈")
    String name,
    @Schema(description = "게시글 작성 시간", example = "2024-04-29T13:00:00")
    LocalDateTime time,
    @Schema(description = "게시글 제목", example = "오늘 나랑 같이 PC방 갈 사람!!!")
    String title,
    @Schema(description = "게시글 내용", example = "구라지롱~")
    String content,
    @Schema(description = "게시글 이미지 URL", example = "www.example.com")
    String path,
    @Schema(description = "댓글 정보")
    List<CommentDTO> comments
) {

}
