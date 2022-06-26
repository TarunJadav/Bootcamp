package com.BugTracker.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BugTracker.entity.Bug;
import com.BugTracker.entity.Project;
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
	public List<Bug> findAllByProjects(Project project) {

		return bugRepository.findAllByProject(project);
	}

	@Override
	public List<Bug> findAllByUser(User user) {

		return bugRepository.findAllByUser(user);
	}

	@Override
	public List<Bug> findAllByTester(User user) {

		return bugRepository.findAllByTester(user);
	}

	@Override
	public List<Bug> findAllByPriority(String priority) {

		return bugRepository.findAllByPriority(priority);
	}

}
