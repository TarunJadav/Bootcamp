package com.BugTracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.BugTracker.entity.Bug;
import com.BugTracker.entity.Project;
import com.BugTracker.entity.Team;
import com.BugTracker.entity.User;

@Repository
public interface BugRepository extends JpaRepository<Bug, Long> {

	List<Bug> findAllByTeams(Team team);

	List<Bug> findAllByProject(Project project);

	List<Bug> findAllByTester(User user);

	Bug findByTeams(Team team);
}
