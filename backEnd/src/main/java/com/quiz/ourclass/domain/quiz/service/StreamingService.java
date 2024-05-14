package com.quiz.ourclass.domain.quiz.service;

import com.quiz.ourclass.domain.quiz.dto.GamerDTO;

public interface StreamingService {

    public void sendGamer(GamerDTO message);
}
