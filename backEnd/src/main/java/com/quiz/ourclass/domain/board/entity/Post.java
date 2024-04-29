package com.quiz.ourclass.domain.board.entity;

import com.quiz.ourclass.domain.board.dto.PostRequest;
import com.quiz.ourclass.domain.member.entity.Member;
import com.quiz.ourclass.domain.organization.entity.Organization;
import com.quiz.ourclass.global.exception.ErrorCode;
import com.quiz.ourclass.global.exception.GlobalException;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    Member author;
    @ManyToOne(fetch = FetchType.LAZY)
    Organization organization;
    String title;
    String content;
    LocalDateTime createTime;
    LocalDateTime updateTime;
    boolean secretStatus;
    @Enumerated(EnumType.STRING)
    Category category;

    @Builder
    public Post(Member member, Organization organization, PostRequest request) {
        LocalDateTime now = LocalDateTime.now();
        Category category;
        try {
            category = Category.valueOf(request.getType().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new GlobalException(ErrorCode.TYPE_NAME_ERROR);
        }
        this.author = member;
        this.organization = organization;
        this.title = request.getTitle();
        this.content = request.getContent();
        this.createTime = now;
        this.updateTime = now;
        this.secretStatus = request.getAnonymous();
        this.category = category;
    }

    public Post() {

    }
}
