package com.quiz.ourClass.domain.chat.entity;

import com.quiz.ourClass.domain.organization.entity.Organization;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @OneToOne(fetch = FetchType.LAZY)
    Organization organization;
}
