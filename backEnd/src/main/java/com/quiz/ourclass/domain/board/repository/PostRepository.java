package com.quiz.ourclass.domain.board.repository;

import com.quiz.ourclass.domain.board.entity.Post;
import com.quiz.ourclass.domain.member.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findByIdAndAuthor(Long postId, Member member);
}
