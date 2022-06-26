package com.BugTracker.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BugTracker.entity.Project;
import com.BugTracker.entity.Report;
import com.BugTracker.repository.ReportRepository;

import com.BugTracker.service.ReportService;

@Service
public class ReportServiceImpl implements ReportService {

	@Autowired
	private ReportRepository reportRepository;

	@Override
	public Report saveReport(Report report) {

		return reportRepository.save(report);
	}

	@Override
	public Report findByPid(Project project) {

		return reportRepository.findByPid(project);
	}

	@Override
	public List<Report> findAllReport() {

		return reportRepository.findAll();
	}

	@Override
	public Report getById(Long id) {

		return reportRepository.findById(id).get();
	}

}
