package com.quiz.ourClass.domain.member.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String email;
    String name;
    String profileImage;
    @Enumerated(EnumType.STRING)
    SocialType socialType;
    @Enumerated(EnumType.STRING)
    Role role;
}