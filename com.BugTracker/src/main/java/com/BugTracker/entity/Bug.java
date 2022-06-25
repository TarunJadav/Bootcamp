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
	private User user;

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

	

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Bug(Long id, String bug_desc, String status, User tester, Project project, User user) {
		super();
		this.id = id;
		this.bug_desc = bug_desc;
		this.status = status;
		this.tester = tester;
		this.project = project;
		this.user = user;
	}

	public Bug() {
		super();
	}

}
