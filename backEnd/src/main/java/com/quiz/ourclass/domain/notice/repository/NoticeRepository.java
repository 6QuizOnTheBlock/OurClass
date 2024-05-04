package com.quiz.ourclass.domain.notice.repository;

import com.quiz.ourclass.domain.notice.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

}
