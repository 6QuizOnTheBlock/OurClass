package com.quiz.ourclass.domain.quiz.repository.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class QuizRepositoryQuerydslImpl implements QuizRepositoryQuerydsl {

    private final JPAQueryFactory queryFactory;

}
