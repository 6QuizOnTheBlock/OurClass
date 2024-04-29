package com.quiz.ourclass.domain.board.repository;

import com.quiz.ourclass.domain.board.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
