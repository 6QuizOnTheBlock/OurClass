package com.quiz.ourclass.domain.quiz.repository.querydsl;

import static com.quiz.ourclass.domain.member.entity.QMember.member;
import static com.quiz.ourclass.domain.organization.entity.QMemberOrganization.memberOrganization;
import static com.quiz.ourclass.domain.organization.entity.QOrganization.organization;
import static com.quiz.ourclass.domain.quiz.entity.QQuizGame.quizGame;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.quiz.ourclass.domain.member.entity.Member;
import com.quiz.ourclass.global.exception.ErrorCode;
import com.quiz.ourclass.global.exception.GlobalException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class QuizRepositoryQuerydslImpl implements QuizRepositoryQuerydsl {

    private final JPAQueryFactory queryFactory;

    // 현재 quiz URL을 요청한 멤버 ID가 URL을 요청할 자격이 있는지 검증합니다.
    public boolean canItGetUrl(Long quizGameId, long memberId) {

        return memberId == Optional.ofNullable((queryFactory
            .select(organization.manager.id)
            .from(organization)
            .leftJoin(quizGame)
            .on(organization.id.eq(quizGame.organization.id))
            .where(quizGame.id.eq(quizGameId))
            .fetchOne())).orElse(-1L);

    }

    @Override
    public List<Member> sendUrl4Member(long quizGameId) {

        return Optional.ofNullable(queryFactory
            .select(member)
            .from(member)
            .leftJoin(memberOrganization).fetchJoin()
            .on(member.eq(memberOrganization.member))
            .leftJoin(organization).fetchJoin()
            .on(organization.eq(memberOrganization.organization))
            .leftJoin(quizGame).fetchJoin()
            .on(organization.eq(quizGame.organization))
            .where(quizGame.id.eq(quizGameId))
            .fetch()).orElseThrow(() -> new GlobalException(ErrorCode.NO_QUIZ_GAME));
    }
}
