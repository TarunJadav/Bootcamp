package com.BugTracker.service;

import java.util.List;

import com.BugTracker.entity.Bug;
import com.BugTracker.entity.Project;
import com.BugTracker.entity.Team;
import com.BugTracker.entity.User;

public interface BugService {

	
	Bug saveBug(Bug bug);
	
	Bug getBugById(Long id);
	
	List<Bug> getAllBugs();
	
	List<Bug> findAllByTeams(Team team);
	
	List<Bug> findAllByProjects(Project project);
	
	List<Bug> findAllByUsers(User user);
	
	Bug findByTeam(Team team);
	
  


	
	
}
