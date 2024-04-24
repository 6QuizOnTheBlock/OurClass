package com.quiz.ourClass.domain.board.entity;

import com.quiz.ourClass.domain.member.entity.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @ManyToOne(fetch = FetchType.LAZY)
    Member member;
    String title;
    String content;
    LocalDateTime createTime;
    LocalDateTime updateTime;
    boolean secretStatus;
    @Enumerated(EnumType.STRING)
    Category category;
}
