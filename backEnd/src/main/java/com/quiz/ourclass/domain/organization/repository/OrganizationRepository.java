package com.quiz.ourclass.domain.organization.repository;

import com.quiz.ourclass.domain.organization.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {

}
