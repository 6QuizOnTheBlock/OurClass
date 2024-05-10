package com.quiz.ourclass.domain.quiz.mapper;

import com.quiz.ourclass.domain.organization.entity.Organization;
import com.quiz.ourclass.domain.organization.repository.OrganizationRepository;
import com.quiz.ourclass.domain.quiz.dto.QuizDTO;
import com.quiz.ourclass.domain.quiz.dto.QuizGameDTO;
import com.quiz.ourclass.domain.quiz.dto.request.MakingQuizRequest;
import com.quiz.ourclass.domain.quiz.entity.Quiz;
import com.quiz.ourclass.domain.quiz.entity.QuizGame;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = OrganizationRepository.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface QuizGameMapper {


    @Mapping(source = "organization", target = "organization")
    QuizGame toQuizGame(MakingQuizRequest makingQuizRequest, Organization organization);

    Quiz toQuiz(QuizGame quizGame, QuizDTO quizDTO);

    QuizGameDTO toQuizGameDTO(QuizGame QuizGame);

}
