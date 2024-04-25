package com.quiz.ourclass.domain.challenge.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class ChallengeGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @ManyToOne(fetch = FetchType.LAZY)
    Challenge challenge;
    @OneToMany(mappedBy = "challengeGroup")
    List<GroupMember> groupMember;
    long leaderId;
    String name;
    boolean completeStatus;
    LocalDateTime createTime;
}
