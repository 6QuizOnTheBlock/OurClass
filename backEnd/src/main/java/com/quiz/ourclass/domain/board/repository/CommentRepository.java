package com.quiz.ourclass.domain.board.repository;

import com.quiz.ourclass.domain.board.entity.Comment;
import com.quiz.ourclass.domain.board.entity.Post;
import com.quiz.ourclass.domain.member.entity.Member;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByPostId(Long postId);

    void deleteByParentId(Long parentId);

    void deleteByPostId(Long postId);

    boolean existsByPostAndMember(Post post, Member member);

    boolean existsByParentIdAndMember(Long parentId, Member member);
}
