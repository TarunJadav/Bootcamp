package com.BugTracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.BugTracker.entity.Project;
import com.BugTracker.entity.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

	Report findByPid(Project project);

}
