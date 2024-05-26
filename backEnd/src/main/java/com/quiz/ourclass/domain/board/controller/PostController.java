package com.quiz.ourclass.domain.board.controller;

import com.quiz.ourclass.domain.board.dto.request.PostRequest;
import com.quiz.ourclass.domain.board.dto.request.PostSliceRequest;
import com.quiz.ourclass.domain.board.dto.request.UpdatePostRequest;
import com.quiz.ourclass.domain.board.dto.response.PostDetailResponse;
import com.quiz.ourclass.domain.board.dto.response.PostListResponse;
import com.quiz.ourclass.domain.board.sevice.PostService;
import com.quiz.ourclass.global.dto.ResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
public class PostController implements PostControllerDocs {

    private final PostService postService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResultResponse<Long>> postWrite(
        @RequestParam("organizationId") Long organizationId,
        @RequestPart(value = "request") PostRequest request,
        @RequestPart(value = "file", required = false) MultipartFile file) {
        Long postId = postService.postWrite(organizationId, file, request);
        return ResponseEntity.ok(ResultResponse.success(postId));
    }

    @PatchMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResultResponse<Long>> postModify(
        @PathVariable(value = "id") Long id,
        @RequestPart(value = "request") UpdatePostRequest request,
        @RequestPart(value = "file", required = false) MultipartFile file) {
        Long postId = postService.postModify(id, file, request);
        return ResponseEntity.ok(ResultResponse.success(postId));
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<ResultResponse<Boolean>> postDelete(@PathVariable(value = "id") Long id) {
        Boolean isDelete = postService.postDelete(id);
        return ResponseEntity.ok(ResultResponse.success(isDelete));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ResultResponse<PostDetailResponse>> postDetailView(
        @PathVariable(value = "id") Long id) {
        PostDetailResponse response = postService.postDetailView(id);
        return ResponseEntity.ok(ResultResponse.success(response));
    }

    @PostMapping(value = "/{id}/report")
    public ResponseEntity<ResultResponse<Boolean>> postReport(
        @PathVariable(value = "id") Long id) {
        boolean isReport = postService.postReport(id);
        return ResponseEntity.ok(ResultResponse.success(isReport));
    }

    @GetMapping
    public ResponseEntity<ResultResponse<PostListResponse>> postListView(
        PostSliceRequest request) {
        PostListResponse response = postService.postListView(request);
        return ResponseEntity.ok(ResultResponse.success(response));
    }
}
