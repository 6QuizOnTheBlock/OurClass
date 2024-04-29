package com.quiz.ourclass.domain.board.controller;

import com.quiz.ourclass.domain.board.dto.PostRequest;
import com.quiz.ourclass.domain.board.sevice.PostService;
import com.quiz.ourclass.global.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class PostController {

    private final PostService postService;

    @Operation(summary = "게시글 작성", description = "입력으로 들어온 dto, multipart 로 게시글을 작성합니다.")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "200", description = "(message : \"Success\", code : 200)",
        content = @Content(schema = @Schema(implementation = Long.class)))
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "400", description = "(message : \"게시판 유형에 맞지 않는 입력입니다.\", code : 400)", content = @Content)
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "404", description = "(message : \"멤버가 존재하지 않습니다.\", code : 404)", content = @Content)
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "500", description = "(message : \"첨부한 파일이 S3에 업로드 되지 않았습니다.\", code : 500)", content = @Content)
    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<ApiResponse<?>> write(
        @RequestParam("classId") Long classId, @RequestPart(value = "request") PostRequest request,
        @RequestPart(value = "file", required = false) MultipartFile file)
        throws IOException {
        Long postId = postService.write(classId, file, request).getData();
        return ResponseEntity.ok(ApiResponse.success(postId));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> modify(@PathVariable Long id,
        @RequestBody PostRequest request) {
        Long postId = postService.modify(id, request).getData();
        return ResponseEntity.ok(ApiResponse.success(postId));
    }
}
