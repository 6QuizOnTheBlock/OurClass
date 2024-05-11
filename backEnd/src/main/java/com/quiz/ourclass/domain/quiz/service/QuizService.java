package com.quiz.ourclass.domain.quiz.service;

import com.quiz.ourclass.domain.quiz.dto.QuizGameDTO;
import com.quiz.ourclass.domain.quiz.dto.request.MakingQuizRequest;
import java.util.List;

public interface QuizService {

    public void makingQuiz(MakingQuizRequest request);

    public List<QuizGameDTO> getQuizList(long orgId);

    public String getQuizUrl(long id);
}
