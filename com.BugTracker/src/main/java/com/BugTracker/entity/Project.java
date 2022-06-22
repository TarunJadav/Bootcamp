package com.BugTracker.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Project {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique =true)
	private String projectname;
	private String techonology;
	private String status;
	private boolean isdeleted;
	private String startdate;

	@ManyToMany
	private List<Team> teams = new ArrayList<Team>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProjectname() {
		return projectname;
	}

	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}

	public String getTechonology() {
		return techonology;
	}

	public void setTechonology(String techonology) {
		this.techonology = techonology;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isIsdeleted() {
		return isdeleted;
	}

	public void setIsdeleted(boolean isdeleted) {
		this.isdeleted = isdeleted;
	}

	public List<Team> getTeams() {
		return teams;
	}

	public void setTeams(List<Team> teams) {
		this.teams = teams;
	}

	public String getStartdate() {
		return startdate;
	}

	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	public Project(Long id, String projectname, String techonology, String status, boolean isdeleted, String startdate,
			List<Team> teams) {
		super();
		this.id = id;
		this.projectname = projectname;
		this.techonology = techonology;
		this.status = status;
		this.isdeleted = isdeleted;
		this.startdate = startdate;
		this.teams = teams;
	}

	public Project() {
		super();
	}

}
