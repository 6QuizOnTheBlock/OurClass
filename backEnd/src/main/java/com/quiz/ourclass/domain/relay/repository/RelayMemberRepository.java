package com.quiz.ourclass.domain.relay.repository;

import com.quiz.ourclass.domain.relay.entity.RelayMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RelayMemberRepository extends JpaRepository<RelayMember, Long> {

    int countByCurMemberIdAndNextMemberId(long curMemberId, long nextMemberId);
}
