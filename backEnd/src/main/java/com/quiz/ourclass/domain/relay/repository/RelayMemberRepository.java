package com.quiz.ourclass.domain.relay.repository;

import com.quiz.ourclass.domain.member.entity.Member;
import com.quiz.ourclass.domain.relay.entity.Relay;
import com.quiz.ourclass.domain.relay.entity.RelayMember;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RelayMemberRepository extends JpaRepository<RelayMember, Long> {

    int countByCurMemberIdAndNextMemberId(long curMemberId, long nextMemberId);

    Optional<RelayMember> findFirstByRelayAndNextMemberOrderByTurnDesc(Relay relay, Member member);
}
