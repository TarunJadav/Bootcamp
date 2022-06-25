package com.BugTracker.service;

import java.util.List;

import com.BugTracker.entity.Bug;
import com.BugTracker.entity.Project;

import com.BugTracker.entity.User;

public interface BugService {

	
	Bug saveBug(Bug bug);
	
	Bug getBugById(Long id);
	
	List<Bug> getAllBugs();
	
	List<Bug> findAllByTester(User user);
	
	List<Bug> findAllByProjects(Project project);
	
	List<Bug> findAllByUser(User user);
	

	
  


	
	
}
