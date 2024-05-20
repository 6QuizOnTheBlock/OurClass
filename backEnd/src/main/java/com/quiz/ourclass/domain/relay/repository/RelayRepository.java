package com.quiz.ourclass.domain.relay.repository;

import com.quiz.ourclass.domain.organization.entity.Organization;
import com.quiz.ourclass.domain.relay.entity.Relay;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RelayRepository extends JpaRepository<Relay, Long>, RelayRepositoryQuerydsl {

    Boolean existsByOrganizationAndEndStatusIsFalse(Organization organization);

    Optional<Relay> findByOrganizationIdAndEndStatusIsFalse(long organizationId);

    List<Relay> findAllByEndStatusIsFalse();
}
