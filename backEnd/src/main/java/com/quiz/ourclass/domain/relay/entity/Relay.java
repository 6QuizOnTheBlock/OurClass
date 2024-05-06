package com.quiz.ourclass.domain.relay.entity;

import com.quiz.ourclass.domain.member.entity.Member;
import com.quiz.ourclass.domain.organization.entity.Organization;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Relay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @ManyToOne(fetch = FetchType.LAZY)
    Organization organization;
    @ManyToOne(fetch = FetchType.LAZY)
    RelayMember currentRunner;
    @ManyToOne(fetch = FetchType.LAZY)
    Member startMember;
    @ManyToOne(fetch = FetchType.LAZY)
    Member lastMember;
    @OneToMany(mappedBy = "relay")
    List<RelayMember> relayMembers;
    int totalCount; //총횟수 돌고나면 종료
    long timeout; //일정 시간 안넘기면 종료
    boolean endStatus;
}
