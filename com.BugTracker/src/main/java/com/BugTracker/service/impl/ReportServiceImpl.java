package com.BugTracker.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BugTracker.entity.Report;
import com.BugTracker.repository.ReportRepository;
import com.BugTracker.service.ReportService;

@Service
public class ReportServiceImpl implements ReportService{

	@Autowired
	private ReportRepository reportRepository;
	
	
	@Override
	public Report saveReport(Report report) {
		
		return reportRepository.save(report);
	}

	
	
}
