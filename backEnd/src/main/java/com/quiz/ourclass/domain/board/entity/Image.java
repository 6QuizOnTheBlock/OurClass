package com.quiz.ourclass.domain.board.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @OneToOne(mappedBy = "image", fetch = FetchType.LAZY)
    Post post;
    String originalName;
    String path;
    LocalDateTime createTime;
    String hash;

    public Image(String originalName, String path, LocalDateTime createTime, String hash) {
        this.originalName = originalName;
        this.path = path;
        this.createTime = createTime;
        this.hash = hash;
    }

    public Image() {
    }
}
