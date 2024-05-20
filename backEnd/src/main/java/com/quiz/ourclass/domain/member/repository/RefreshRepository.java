package com.quiz.ourclass.domain.member.repository;

import com.quiz.ourclass.domain.member.entity.Refresh;
import java.util.Optional;
import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshRepository extends KeyValueRepository<Refresh, Long> {

    Optional<Refresh> findByMemberId(long id);

    Optional<Refresh> findByAccessToken(String accessToken);

}
