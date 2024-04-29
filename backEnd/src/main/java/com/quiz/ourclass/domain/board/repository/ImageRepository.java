package com.quiz.ourclass.domain.board.repository;

import com.quiz.ourclass.domain.board.entity.Image;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {

    Optional<Image> findByPost_Id(Long postId);
}
