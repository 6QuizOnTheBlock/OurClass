package com.quiz.ourclass.domain.board.controller;

import com.quiz.ourclass.domain.board.dto.request.CommentRequest;
import com.quiz.ourclass.domain.board.dto.request.UpdateCommentRequest;
import com.quiz.ourclass.domain.board.sevice.CommentService;
import com.quiz.ourclass.global.dto.ResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    public ResponseEntity<ResultResponse<Long>> commentWrite(
        @RequestBody CommentRequest request) {
        Long commentId = commentService.write(request);
        return ResponseEntity.ok(ResultResponse.success(commentId));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResultResponse<Long>> commentModify(
        @PathVariable(value = "id") Long id,
        @RequestBody UpdateCommentRequest request) {
        Long commentId = commentService.modify(id, request);
        return ResponseEntity.ok(ResultResponse.success(commentId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResultResponse<Boolean>> commentDelete(
        @PathVariable(value = "id") Long id) {
        Boolean isDelete = commentService.delete(id);
        return ResponseEntity.ok(ResultResponse.success(isDelete));
    }

    @PostMapping("/{id}/report")
    public ResponseEntity<ResultResponse<Boolean>> commentReport(
        @PathVariable(value = "id") Long id) {
        Boolean isReport = commentService.report(id);
        return ResponseEntity.ok(ResultResponse.success(isReport));
    }
}
