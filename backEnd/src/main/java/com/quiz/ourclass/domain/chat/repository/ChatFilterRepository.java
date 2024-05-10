package com.quiz.ourclass.domain.chat.repository;

import com.quiz.ourclass.domain.chat.entity.ChatFilter;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatFilterRepository extends JpaRepository<ChatFilter, Long> {

    Optional<ChatFilter> findByBadWord(String badWord);
}
