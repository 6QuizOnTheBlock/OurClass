package com.quiz.ourclass.domain.quiz.repository.jpa;

import com.quiz.ourclass.domain.quiz.entity.QuizGame;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizGameRepository extends JpaRepository<QuizGame, Long> {

    List<QuizGame> findAllByOrganization_Id(long orgId);

}
