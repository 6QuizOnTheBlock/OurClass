package com.quiz.ourclass.domain.quiz.controller;

import com.quiz.ourclass.domain.quiz.controller.docs.QuizControllerDocs;
import com.quiz.ourclass.domain.quiz.dto.request.MakingQuizRequest;
import com.quiz.ourclass.domain.quiz.service.QuizServiceImpl;
import com.quiz.ourclass.global.dto.ResultResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/quizzes")
@RequiredArgsConstructor
@Slf4j
public class QuizController implements QuizControllerDocs {

    public final QuizServiceImpl quizService;

    @PutMapping("")
    public ResponseEntity<ResultResponse<?>> makingQuiz(@RequestBody MakingQuizRequest request) {
        quizService.makingQuiz(request);
        return ResponseEntity.ok(ResultResponse.success(null));
    }

    @GetMapping("/{orgId}")
    public ResponseEntity<ResultResponse<?>> getQuizList(@PathVariable("orgId") long orgId) {
        return ResponseEntity.ok(ResultResponse.success(quizService.getQuizList(orgId)));
    }

    @GetMapping("/code/{quizGameId}")
    public ResponseEntity<ResultResponse<?>> getQuizUrl(
        @PathVariable("quizGameId") long quizGameId) {
        return ResponseEntity.ok(ResultResponse.success(quizService.getQuizUrl(quizGameId)));
    }

    @GetMapping("/ranking/{quizGameId}")
    public ResponseEntity<ResultResponse<?>> getRanking(
        @PathVariable("quizGameId") long quizGameId) {
        return ResponseEntity.ok(ResultResponse.success(quizService.getRanking(quizGameId)));
    }
}
