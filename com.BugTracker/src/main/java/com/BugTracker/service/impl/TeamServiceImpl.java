package com.BugTracker.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.BugTracker.entity.Project;
import com.BugTracker.entity.Team;
import com.BugTracker.entity.User;
import com.BugTracker.repository.TeamRepository;
import com.BugTracker.service.TeamService;

@Service
public class TeamServiceImpl implements TeamService {


	
	@Autowired
	private TeamRepository teamRepository;

	@Override
	public Team saveTeam(Team team) {

		return teamRepository.save(team);
	}

	@Override
	public List<Team> getAllTeams() {

		return teamRepository.findAll();
	}

	@Override
	public Team getTeamById(Long id) {

		return teamRepository.findById(id).get();
	}

	@Override
	public void deleteTeamById(Long id) {

		teamRepository.deleteById(id);

	}

	@Override
	public List<Team> findAllByUsers(User user) {
		
		return teamRepository.findAllByUsers(user);
	}

	@Override
	public List<Team> findAllByProjects(Project project) {
		
		return teamRepository.findAllByProjects(project);
	}

	
	
	

}
