package com.quiz.ourclass.domain.organization.entity;

import com.quiz.ourclass.domain.member.entity.Member;
import com.quiz.ourclass.domain.organization.dto.request.UpdateExpRequest;
import com.quiz.ourclass.domain.organization.dto.response.UpdateExpResponse;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberOrganization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @ManyToOne(fetch = FetchType.LAZY)
    Organization organization;
    @ManyToOne(fetch = FetchType.LAZY)
    Member member;
    double isolationPoint;
    int exp;

    public UpdateExpResponse updateExp(UpdateExpRequest updateExpRequest) {
        this.exp += updateExpRequest.exp();
        return UpdateExpResponse.builder().exp(exp).build();
    }
}
