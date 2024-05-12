package com.quiz.ourclass.domain.organization.entity;

import com.quiz.ourclass.domain.member.entity.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Getter
@Entity
public class Relationship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @ManyToOne(fetch = FetchType.LAZY)
    Organization organization;
    @ManyToOne(fetch = FetchType.LAZY)
    Member member1; //memberId가 작은것이 member1
    @ManyToOne(fetch = FetchType.LAZY)
    Member member2;
    int relationPoint;
    int tagGreetingCount;
    int designGroupCount;
    int freeGroupCount;

    public int updateTagGreetingCount() {
        this.tagGreetingCount += 1;
        return this.tagGreetingCount;
    }

    public int updateDesignGroupCount() {
        this.designGroupCount += 1;
        return this.designGroupCount;
    }

    public int updateFreeGroupCount() {
        this.freeGroupCount += 1;
        return this.freeGroupCount;
    }
}
