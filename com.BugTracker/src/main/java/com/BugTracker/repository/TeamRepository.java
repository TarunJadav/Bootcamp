package com.BugTracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.BugTracker.entity.Bug;
import com.BugTracker.entity.Project;
import com.BugTracker.entity.Team;
import com.BugTracker.entity.User;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
	
    List<Team> findAllByUsers(User user);
    
    List<Team> findAllByProjects(Project project);
    
    List<Team> findAllByBugs(Bug bug);

}
