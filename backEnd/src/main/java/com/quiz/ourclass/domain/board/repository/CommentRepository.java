package com.quiz.ourclass.domain.board.repository;

import com.quiz.ourclass.domain.board.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
