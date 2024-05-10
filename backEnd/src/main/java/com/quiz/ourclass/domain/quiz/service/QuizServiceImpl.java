package com.quiz.ourclass.domain.quiz.service;

import com.quiz.ourclass.domain.organization.entity.Organization;
import com.quiz.ourclass.domain.organization.repository.OrganizationRepository;
import com.quiz.ourclass.domain.quiz.dto.request.MakingQuizRequest;
import com.quiz.ourclass.domain.quiz.entity.QuizGame;
import com.quiz.ourclass.domain.quiz.mapper.QuizGameMapper;
import com.quiz.ourclass.domain.quiz.repository.jpa.QuizGameRepository;
import com.quiz.ourclass.domain.quiz.repository.jpa.QuizRepository;
import com.quiz.ourclass.global.exception.ErrorCode;
import com.quiz.ourclass.global.exception.GlobalException;
import com.quiz.ourclass.global.util.UserAccessUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuizServiceImpl {

    public final UserAccessUtil accessUtil;
    public final OrganizationRepository organizationRepository;
    public final QuizGameRepository quizGameRepository;
    public final QuizRepository quizRepository;
    public final QuizGameMapper quizGameMapper;

    public void makingQuiz(MakingQuizRequest request) {
        // 0. orgId로 들어온 단체 찾기
        Organization org = organizationRepository.findById(request.orgId())
            .orElseThrow(() -> new GlobalException(ErrorCode.ORGANIZATION_NOT_FOUND));
        // 1. 단체 ID 담당자랑, 퀴즈 만들겠다는 단체랑 매칭 되는지 확인
        if (!org.getManager().equals(accessUtil.getMember().orElse(null))) {
            throw new GlobalException(ErrorCode.ORGANIZATION_NOT_MATCH);
        }
        // 2. 매칭이 된다면, 해당 단체에 귀속 되는 퀴즈 게임 만들기
        QuizGame quizGame = quizGameRepository.save(quizGameMapper.toQuizGame(request,
            org));
        // 3. 해당 퀴즈 게임에 귀속 되는 퀴즈들 생성
        request.quizList().stream()
            .map(quizDTO -> quizGameMapper.toQuiz(quizGame, quizDTO))
            .forEach(quizRepository::save);
    }

}
