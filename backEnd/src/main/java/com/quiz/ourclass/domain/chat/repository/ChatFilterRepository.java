package com.quiz.ourclass.domain.chat.repository;

import com.quiz.ourclass.domain.chat.entity.ChatFilter;
import com.quiz.ourclass.domain.organization.entity.Organization;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatFilterRepository extends JpaRepository<ChatFilter, Long> {

    Optional<ChatFilter> findByOrganizationAndBadWord(Organization organization, String badWord);

    Page<ChatFilter> findByOrganizationOrderById(Organization organization, Pageable pageable);
}
