package com.quiz.ourclass.domain.board.entity;

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
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
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
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    List<Comment> comments = new ArrayList<>();
    @OneToOne(fetch = FetchType.LAZY)
    Image image;
    String title;
    String content;
    LocalDateTime createTime;
    LocalDateTime updateTime;
    boolean secretStatus;
    @Enumerated(EnumType.STRING)
    PostCategory postCategory;
}
