package com.quiz.ourclass.domain.board.entity;

import com.quiz.ourclass.domain.member.entity.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @ManyToOne(fetch = FetchType.LAZY)
    Member member;
    @ManyToOne(fetch = FetchType.LAZY)
    Post post;
    String content;
    LocalDateTime createTime;
    LocalDateTime updateTime;
    Long parentId;
}
