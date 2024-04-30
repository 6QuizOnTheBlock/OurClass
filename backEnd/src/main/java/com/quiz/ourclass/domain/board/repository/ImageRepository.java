package com.quiz.ourclass.domain.board.repository;

import com.quiz.ourclass.domain.board.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {

}
