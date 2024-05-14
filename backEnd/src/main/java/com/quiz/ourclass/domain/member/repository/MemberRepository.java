package com.quiz.ourclass.domain.member.repository;

import com.quiz.ourclass.domain.member.entity.Member;
import com.quiz.ourclass.domain.member.repository.querydsl.MemberRepositoryQuerydsl;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryQuerydsl {

    Optional<Member> findByEmail(String email);

    Optional<Member> findByName(String name);
}
