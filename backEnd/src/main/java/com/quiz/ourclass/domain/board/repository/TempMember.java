package com.quiz.ourclass.domain.board.repository;

import com.quiz.ourclass.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TempMember extends JpaRepository<Member, Long> {

}
