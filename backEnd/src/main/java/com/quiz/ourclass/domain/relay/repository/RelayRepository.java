package com.quiz.ourclass.domain.relay.repository;

import com.quiz.ourclass.domain.organization.entity.Organization;
import com.quiz.ourclass.domain.relay.entity.Relay;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RelayRepository extends JpaRepository<Relay, Long> {

    Boolean existsByOrganizationAndEndStatusIsFalse(Organization organization);
}
