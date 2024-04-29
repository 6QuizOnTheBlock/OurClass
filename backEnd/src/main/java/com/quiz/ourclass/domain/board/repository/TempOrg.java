package com.quiz.ourclass.domain.board.repository;

import com.quiz.ourclass.domain.organization.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TempOrg extends JpaRepository<Organization, Long> {

}
