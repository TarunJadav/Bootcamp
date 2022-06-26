package com.BugTracker.service;

import java.util.List;

import com.BugTracker.entity.Project;
import com.BugTracker.entity.Report;

public interface ReportService {

	Report saveReport(Report report);

	Report findByPid(Project project);

	List<Report> findAllReport();

	Report getById(Long id);

}
