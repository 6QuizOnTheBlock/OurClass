package com.quiz.ourclass.domain.challenge.repository;

import com.quiz.ourclass.domain.challenge.entity.GroupMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {

}
