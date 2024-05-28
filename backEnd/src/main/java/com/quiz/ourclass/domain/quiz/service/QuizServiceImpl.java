package com.quiz.ourclass.domain.quiz.service;

import com.quiz.ourclass.domain.member.entity.Member;
import com.quiz.ourclass.domain.member.entity.Role;
import com.quiz.ourclass.domain.organization.entity.Organization;
import com.quiz.ourclass.domain.organization.repository.OrganizationRepository;
import com.quiz.ourclass.domain.quiz.dto.GamerDTO;
import com.quiz.ourclass.domain.quiz.dto.QuizGameDTO;
import com.quiz.ourclass.domain.quiz.dto.request.MakingQuizRequest;
import com.quiz.ourclass.domain.quiz.entity.Quiz;
import com.quiz.ourclass.domain.quiz.entity.QuizGame;
import com.quiz.ourclass.domain.quiz.mapper.QuizGameMapper;
import com.quiz.ourclass.domain.quiz.repository.jpa.QuizGameRepository;
import com.quiz.ourclass.domain.quiz.repository.jpa.QuizRepository;
import com.quiz.ourclass.global.exception.ErrorCode;
import com.quiz.ourclass.global.exception.GlobalException;
import com.quiz.ourclass.global.util.FcmUtil;
import com.quiz.ourclass.global.util.RedisUtil;
import com.quiz.ourclass.global.util.UserAccessUtil;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuizServiceImpl implements QuizService {

    private final UserAccessUtil accessUtil;
    private final RedisUtil redisUtil;
    private final FcmUtil fcmUtil;
    private final OrganizationRepository organizationRepository;
    private final QuizGameRepository quizGameRepository;
    private final QuizRepository quizRepository;
    private final QuizGameMapper quizGameMapper;
    private final CountdownService countdownService;

    @Value("${ulvan.url}")
    private String ulvanUrl;


    @Transactional
    public void makingQuiz(MakingQuizRequest request) {
        // 0. orgId로 들어온 단체 찾기
        Organization org = organizationRepository.findById(request.orgId())
            .orElseThrow(() -> new GlobalException(ErrorCode.ORGANIZATION_NOT_FOUND));
        // 1. 퀴즈를 만들 수 있는 권한이 있는지 체크 (선생님인가)
        Member member = accessUtil.getMember()
            .orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_NOT_FOUND));
        if (!member.getRole().equals(Role.TEACHER)) {
            throw new GlobalException(ErrorCode.ORGANIZATION_NOT_MATCH);
        }
        // 2. 매칭이 된다면, 해당 단체에 귀속 되는 퀴즈 게임 만들기
        QuizGame quizGame = quizGameRepository.save(quizGameMapper.toQuizGame(request, org));
        // 3. 해당 퀴즈 게임에 귀속 되는 퀴즈들 생성
        request.quizList().stream()
            .map(quizDTO -> Quiz.builder()
                .quizGame(quizGame)
                .question(quizDTO.question())
                .answer(quizDTO.answer())
                .point(quizDTO.point())
                .candidate1(quizDTO.candidate1())
                .candidate2(quizDTO.candidate2())
                .candidate3(quizDTO.candidate3())
                .candidate4(quizDTO.candidate4())
                .build())
            .forEach(quizRepository::save);
    }

    public List<QuizGameDTO> getQuizList(long orgId) {
        // List 는 반환 값이 아무것도 없더라도 null을 반환하지 않고, 안에 값이 없는 Empty 상태가 된다.
        List<QuizGame> quizGames = quizGameRepository.findAllByOrganization_Id(orgId);
        // 따라서 empty 상태일 경우, Error 를 반환한다.
        if (quizGames.isEmpty()) {
            throw new GlobalException(ErrorCode.NO_QUIZ_GAME);
        }
        return quizGames.stream()
            .map(quizGameMapper::toQuizGameDTO)
            .toList();
    }

    public String getQuizUrl(long quizGameId) {
        Member me = accessUtil.getMember()
            .orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_NOT_FOUND));
        // 1. [UUID]를 이용해 퀴즈 게임 [URL]을 생성합니다.
        UUID uuid = UUID.randomUUID();
        String url = ulvanUrl + quizGameId + "/" + uuid;
        // 2. [URL]을 [REDIS]에 수명을 10분으로 두고 저장합니다. (퀴즈 방 입장할 때 체크용)
        redisUtil.setQuizGame(quizGameId, uuid);
        // 3. [URL]을 요청 당사자는 물론, 단체에 속한 모두에게 전송 합니다.
        List<Member> members = quizRepository.sendUrl4Member(quizGameId);
        fcmUtil.multiFcmSend(members, quizGameMapper.toFcmDTO("퀴즈를 풀어보아요!", url));
        // 4. 대기방 60초 카운트 다운 시작 -> 60초 지나면 게임 자동 시작
        countdownService.startCountDown();
        return url;
    }

    @Override
    public List<GamerDTO> getRanking(long quizGameId) {
        return quizRepository.getRankingList(quizGameId, redisUtil.getAllMemberScores(quizGameId));
    }
}
