package com.BugTracker.service;

import com.BugTracker.entity.Project;
import com.BugTracker.entity.Report;

public interface ReportService {

	Report saveReport(Report report);
	
	Report findByPid(Project project);

}
