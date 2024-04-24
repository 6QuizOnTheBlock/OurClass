package com.quiz.ourClass.domain.challenge.entity;

import com.quiz.ourClass.domain.organization.entity.Organization;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
public class Challenge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @ManyToOne(fetch = FetchType.LAZY)
    Organization organization;
    String title;
    String content;
    boolean progressStatus;
    int reward;
    LocalDateTime startTime;
    LocalDateTime endTime;
    int minCount;
    int headCount;
}
