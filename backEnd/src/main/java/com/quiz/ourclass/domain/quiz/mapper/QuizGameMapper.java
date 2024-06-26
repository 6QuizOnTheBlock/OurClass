package com.quiz.ourclass.domain.quiz.mapper;

import com.quiz.ourclass.domain.organization.entity.Organization;
import com.quiz.ourclass.domain.organization.repository.OrganizationRepository;
import com.quiz.ourclass.domain.quiz.dto.QuizDTO;
import com.quiz.ourclass.domain.quiz.dto.QuizGameDTO;
import com.quiz.ourclass.domain.quiz.dto.request.MakingQuizRequest;
import com.quiz.ourclass.domain.quiz.entity.Quiz;
import com.quiz.ourclass.domain.quiz.entity.QuizGame;
import com.quiz.ourclass.global.dto.FcmDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Mapper(componentModel = "spring", uses = OrganizationRepository.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface QuizGameMapper {

    @Mapping(source = "organization", target = "organization")
    QuizGame toQuizGame(MakingQuizRequest makingQuizRequest, Organization organization);

    @Transactional
    Quiz toQuiz(QuizGame quizGame, QuizDTO quizDTO);

    QuizGameDTO toQuizGameDTO(QuizGame quizGame);

    FcmDTO toFcmDTO(String title, String body);

}
