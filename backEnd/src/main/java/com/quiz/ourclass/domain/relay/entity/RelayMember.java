package com.quiz.ourclass.domain.relay.entity;

import com.quiz.ourclass.domain.member.entity.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class RelayMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @ManyToOne(fetch = FetchType.LAZY)
    Relay relay;
    @ManyToOne(fetch = FetchType.LAZY)
    Member curMember;
    @ManyToOne(fetch = FetchType.LAZY)
    Member nextMember;
    String question;
    int turn;
    boolean endStatus;
}