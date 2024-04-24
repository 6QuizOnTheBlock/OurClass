package com.quiz.ourClass.domain.notice.entity;

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
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @ManyToOne(fetch = FetchType.LAZY)
    Member receiver;
    String url; //발신자 또는 관련 주소
    String content;
    @Enumerated(EnumType.STRING)
    NoticeType type;
    LocalDateTime createTime;
    boolean readStatus;
}
