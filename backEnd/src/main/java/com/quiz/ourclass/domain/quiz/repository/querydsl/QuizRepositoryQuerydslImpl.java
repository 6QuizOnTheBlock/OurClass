package com.quiz.ourclass.domain.quiz.repository.querydsl;

import static com.quiz.ourclass.domain.member.entity.QMember.member;
import static com.quiz.ourclass.domain.organization.entity.QMemberOrganization.memberOrganization;
import static com.quiz.ourclass.domain.organization.entity.QOrganization.organization;
import static com.quiz.ourclass.domain.quiz.entity.QQuizGame.quizGame;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.quiz.ourclass.domain.member.entity.Member;
import com.quiz.ourclass.domain.quiz.dto.GamerDTO;
import com.quiz.ourclass.domain.quiz.service.GamerStatus;
import com.quiz.ourclass.global.exception.ErrorCode;
import com.quiz.ourclass.global.exception.GlobalException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;

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

    public List<GamerDTO> getRankingList(long quizGameId, Set<TypedTuple<String>> rank) {
        // 멤버 리스트 추출
        List<Long> memberIds = rank.stream()
            .map(score -> Long.parseLong(Objects.requireNonNull(score.getValue())))
            .toList();
        // 멤버 정보 일괄 조회 <ID, Member 객체> Map 만들기
        Map<Long, Member> members = queryFactory.selectFrom(member)
            .where(member.id.in(memberIds))
            .fetch()
            .stream()
            .collect(Collectors.toMap(Member::getId, m -> m));

        return rank.stream()
            .map(person -> {
                Member m = members.get(Long.parseLong(Objects.requireNonNull(person.getValue())));
                return new GamerDTO(
                    m.getId(),
                    quizGameId,
                    m.getProfileImage(),
                    m.getName(),
                    Optional.ofNullable(person.getScore()).orElse(0.0).intValue(),
                    GamerStatus.ENTER
                );
            })
            .toList();
    }


}
