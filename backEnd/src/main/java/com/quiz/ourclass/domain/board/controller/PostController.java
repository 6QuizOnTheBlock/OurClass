package com.quiz.ourclass.domain.board.controller;

import com.quiz.ourclass.domain.board.dto.request.PostRequest;
import com.quiz.ourclass.domain.board.dto.response.PostDetailResponse;
import com.quiz.ourclass.domain.board.sevice.PostService;
import com.quiz.ourclass.global.dto.ResultResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@RequestMapping("/board")
public class PostController implements PostControllerDocs {

    private final PostService postService;

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<ResultResponse<?>> write(
        @RequestParam("classId") Long classId, @RequestPart(value = "request") PostRequest request,
        @RequestPart(value = "file", required = false) MultipartFile file)
        throws IOException {
        Long postId = postService.write(classId, file, request);
        return ResponseEntity.ok(ResultResponse.success(postId));
    }

    @PatchMapping(value = "/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<ResultResponse<?>> modify(@PathVariable(value = "id") Long id,
        @RequestPart(value = "request") PostRequest request,
        @RequestPart(value = "file", required = false) MultipartFile file) throws IOException {
        Long postId = postService.modify(id, file, request);
        return ResponseEntity.ok(ResultResponse.success(postId));
    }
    
    @DeleteMapping(value = "{id}")
    public ResponseEntity<ResultResponse<?>> delete(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(ResultResponse.success(postService.delete(id)));
    }
  
    @GetMapping(value = "/{id}")
    public ResponseEntity<ResultResponse<?>> detailView(@PathVariable(value = "id") Long id) {
        PostDetailResponse response = postService.detailView(id);
        return ResponseEntity.ok(ResultResponse.success(response));
    }
}
