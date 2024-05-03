package com.quiz.ourclass.domain.member.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
public class DefaultImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String photo;

    private DefaultImage(long id, String photo) {
        this.id = id;
        this.photo = photo;
    }

    public static DefaultImage of(long id, String photo) {
        return builder().id(id).photo(photo).build();
    }
}
