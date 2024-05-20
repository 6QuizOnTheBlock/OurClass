package com.quiz.ourclass.domain.quiz.service;

import com.quiz.ourclass.domain.quiz.dto.GamerDTO;
import com.quiz.ourclass.domain.quiz.dto.request.AnswerRequest;
import com.quiz.ourclass.domain.quiz.dto.request.QuestionRequest;

public interface StreamingService {

    public void sendGamer(GamerDTO message);

    public void sendQuestion(QuestionRequest request);

    public void sendAnswer(AnswerRequest request, String accessToken);
}
