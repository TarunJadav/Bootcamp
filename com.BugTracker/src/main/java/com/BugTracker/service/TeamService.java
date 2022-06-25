package com.BugTracker.service;

import java.util.List;
import com.BugTracker.entity.Project;
import com.BugTracker.entity.Team;
import com.BugTracker.entity.User;


public interface TeamService {

	Team saveTeam(Team team);

	List<Team> getAllTeams();

	Team getTeamById(Long id);

	void deleteTeamById(Long id);
	
	List<Team> findAllByUsers(User user);
	
	List<Team> findAllByProjects(Project project);
	
	
	
	

}
