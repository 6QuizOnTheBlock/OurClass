package com.quiz.ourclass.domain.board.entity;

import com.quiz.ourclass.domain.board.dto.request.PostRequest;
import com.quiz.ourclass.domain.member.entity.Member;
import com.quiz.ourclass.domain.organization.entity.Organization;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    Member author;
    @ManyToOne(fetch = FetchType.LAZY)
    Organization organization;
    @OneToOne(fetch = FetchType.LAZY)
    Image image;
    String title;
    String content;
    LocalDateTime createTime;
    LocalDateTime updateTime;
    boolean secretStatus;
    @Enumerated(EnumType.STRING)
    PostCategory postCategory;

    public Post(Member member, Organization organization, Image image, PostRequest request) {
        LocalDateTime now = LocalDateTime.now();

        this.author = member;
        this.organization = organization;
        this.image = image;
        this.title = request.getTitle();
        this.content = request.getContent();
        this.createTime = now;
        this.updateTime = now;
        this.secretStatus = request.getAnonymous();
        this.postCategory = request.getType();
    }

    public Post() {

    }
}
