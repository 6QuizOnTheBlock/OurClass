package com.quiz.ourclass.domain.chat.repository;

import com.quiz.ourclass.domain.chat.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

}
