package com.quiz.ourclass.domain.quiz.controller;

import com.quiz.ourclass.domain.quiz.dto.request.MakingQuizRequest;
import com.quiz.ourclass.domain.quiz.service.QuizServiceImpl;
import com.quiz.ourclass.global.dto.ResultResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/quiz")
@RequiredArgsConstructor
@Slf4j
public class QuizController {

    public final QuizServiceImpl quizService;

    @PutMapping("")
    public ResponseEntity<ResultResponse<?>> makingQuiz(@RequestBody MakingQuizRequest request) {
        quizService.makingQuiz(request);
        return ResponseEntity.ok(ResultResponse.success(null));
    }

    @GetMapping("")
    public ResponseEntity<ResultResponse<?>> getQuizList() {
        return null;
    }
}
