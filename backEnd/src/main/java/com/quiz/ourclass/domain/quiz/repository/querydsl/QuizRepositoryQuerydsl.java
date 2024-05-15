package com.quiz.ourclass.domain.quiz.repository.querydsl;

import com.quiz.ourclass.domain.member.entity.Member;
import com.quiz.ourclass.domain.quiz.dto.GamerDTO;
import java.util.List;
import java.util.Set;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;

public interface QuizRepositoryQuerydsl {

    public boolean canItGetUrl(Long quizGameId, long memberId);

    public List<Member> sendUrl4Member(long quizGameId);

    public List<GamerDTO> getRankingList(long quizGameId, Set<TypedTuple<String>> rank);
}
