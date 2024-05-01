package com.quiz.ourclass.domain.challenge.repository;

import com.quiz.ourclass.domain.challenge.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {

}
