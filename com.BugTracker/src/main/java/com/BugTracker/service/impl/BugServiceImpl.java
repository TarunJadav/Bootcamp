package com.BugTracker.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BugTracker.entity.Bug;
import com.BugTracker.entity.Project;
import com.BugTracker.entity.Team;
import com.BugTracker.entity.User;
import com.BugTracker.repository.BugRepository;
import com.BugTracker.service.BugService;

@Service
public class BugServiceImpl implements BugService {

	@Autowired
	private BugRepository bugRepository;

	@Override
	public Bug saveBug(Bug bug) {

		return bugRepository.save(bug);
	}

	@Override
	public Bug getBugById(Long id) {

		return bugRepository.findById(id).get();
	}

	@Override
	public List<Bug> getAllBugs() {

		return bugRepository.findAll();
	}

	@Override
	public List<Bug> findAllByTeams(Team team) {

		return bugRepository.findAllByTeams(team);
	}

	@Override
	public List<Bug> findAllByProjects(Project project) {
		
		return bugRepository.findAllByProject(project);
	}

	@Override
	public List<Bug> findAllByUsers(User user) {
		
		return bugRepository.findAllByTester(user);
	}

	@Override
	public Bug findByTeam(Team team) {
		
		return bugRepository.findByTeams(team);
	}

	

}
