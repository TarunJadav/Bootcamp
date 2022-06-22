package com.BugTracker.service;

import java.util.List;

import com.BugTracker.entity.Project;
import com.BugTracker.entity.Team;

public interface ProjectService {

	Project saveProject(Project project);

	List<Project> getAllProjects();

	Project getProjectById(Long id);

	void deleteProjectById(Long id);

	List<Project> findByTechonology(String techonology);

	List<Project> findByStatus(String status);
	
	List<Project> findAllByTeams(Team team);

}
