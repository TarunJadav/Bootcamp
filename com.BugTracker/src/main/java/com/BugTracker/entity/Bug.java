package com.BugTracker.entity;



import javax.persistence.CascadeType;

import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import javax.persistence.OneToOne;

@Entity
public class Bug {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String bug_desc;
	private String status;


	@OneToOne(cascade = CascadeType.ALL)
	private User tester;

	@OneToOne
	private Project project;

	@ManyToOne
	private Team teams;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBug_desc() {
		return bug_desc;
	}

	public void setBug_desc(String bug_desc) {
		this.bug_desc = bug_desc;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public User getTester() {
		return tester;
	}

	public void setTester(User tester) {
		this.tester = tester;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}


	public Team getTeams() {
		return teams;
	}

	public void setTeams(Team teams) {
		this.teams = teams;
	}

	public Bug(Long id, String bug_desc, String status, User tester, Project project, Team teams) {
		super();
		this.id = id;
		this.bug_desc = bug_desc;
		this.status = status;
		this.tester = tester;
		this.project = project;
		this.teams = teams;
	}

	public Bug() {
		super();
	}

	

}
