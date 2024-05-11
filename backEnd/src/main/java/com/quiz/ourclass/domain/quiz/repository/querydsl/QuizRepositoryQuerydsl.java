package com.quiz.ourclass.domain.quiz.repository.querydsl;

import com.quiz.ourclass.domain.member.entity.Member;
import java.util.List;

public interface QuizRepositoryQuerydsl {

    public boolean canItGetUrl(Long quizGameId, long memberId);

    public List<Member> sendUrl4Member(long quizGameId);
}
