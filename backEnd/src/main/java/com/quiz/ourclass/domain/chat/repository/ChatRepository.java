package com.quiz.ourclass.domain.chat.repository;

import com.quiz.ourclass.domain.chat.entity.Chat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatRepository extends MongoRepository<Chat, String> {

    Page<Chat> findByRoomIdOrderByIdAsc(Long chatRoomId, Pageable pageable);
}
