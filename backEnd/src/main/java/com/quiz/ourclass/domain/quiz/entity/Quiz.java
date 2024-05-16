package com.quiz.ourclass.domain.quiz.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @ManyToOne(fetch = FetchType.LAZY)
    QuizGame quizGame;
    String question;
    String answer;
    long point;
    String candidate1;
    String candidate2;
    String candidate3;
    String candidate4;


    @Enumerated(EnumType.STRING)
    Quiztype quiztype;

    @Override
    public String toString() {
        return "Quiz{" +
            "id=" + id +
            ", quizGame=" + quizGame +
            ", question='" + question + '\'' +
            ", answer='" + answer + '\'' +
            ", point=" + point +
            ", candidate1='" + candidate1 + '\'' +
            ", candidate2='" + candidate2 + '\'' +
            ", candidate3='" + candidate3 + '\'' +
            ", candidate4='" + candidate4 + '\'' +
            ", quiztype=" + quiztype +
            '}';
    }
}
