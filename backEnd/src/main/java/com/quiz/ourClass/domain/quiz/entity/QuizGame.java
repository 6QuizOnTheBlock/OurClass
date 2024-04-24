package com.quiz.ourClass.domain.quiz.entity;

import com.quiz.ourClass.domain.organization.entity.Organization;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class QuizGame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @ManyToOne(fetch = FetchType.LAZY)
    Organization organization;
    int quizCount;
    long limitTime;
}
