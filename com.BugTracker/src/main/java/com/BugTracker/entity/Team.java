package com.BugTracker.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Team {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String teamname;

	private String teamrole;

	@ManyToMany()
	private Set<User> users = new HashSet<>();

	@ManyToMany(mappedBy = "teams", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Project> projects = new HashSet<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTeamname() {
		return teamname;
	}

	public void setTeamname(String teamname) {
		this.teamname = teamname;
	}

	public Team(Long id, String teamname) {
		super();
		this.id = id;
		this.teamname = teamname;
	}

	public Team() {
		super();
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public Set<Project> getProjects() {
		return projects;
	}

	public void setProjects(Set<Project> projects) {
		this.projects = projects;
	}

	public String getTeamrole() {
		return teamrole;
	}

	public void setTeamrole(String teamrole) {
		this.teamrole = teamrole;
	}

	public Team(Long id, String teamname, String teamrole, Set<User> users, Set<Project> projects) {
		super();
		this.id = id;
		this.teamname = teamname;
		this.teamrole = teamrole;
		this.users = users;
		this.projects = projects;
	}

	

}
