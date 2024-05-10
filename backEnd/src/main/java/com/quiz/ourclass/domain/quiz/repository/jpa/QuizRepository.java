package com.quiz.ourclass.domain.quiz.repository.jpa;

import com.quiz.ourclass.domain.quiz.entity.Quiz;
import com.quiz.ourclass.domain.quiz.repository.querydsl.QuizRepositoryQuerydsl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// QuizEntity 에 대한 JPA 레포지토리 상속과 QueryDsl 상속을 한 번에 해결
@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long>, QuizRepositoryQuerydsl {

}
