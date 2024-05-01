package com.quiz.ourclass.domain.board.controller;

import com.quiz.ourclass.domain.board.dto.request.CommentRequest;
import com.quiz.ourclass.domain.board.sevice.CommentService;
import com.quiz.ourclass.global.dto.ResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController implements CommentControllerDocs {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<ResultResponse<Long>> write(
        @RequestBody CommentRequest request) {
        Long id = commentService.write(request);
        return ResponseEntity.ok(ResultResponse.success(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResultResponse<Long>> modify(
        @PathVariable(value = "id") Long id,
        @RequestBody CommentRequest request) {
        Long commentId = commentService.modify(id, request);
        return ResponseEntity.ok(ResultResponse.success(commentId));
    }
}
