package com.BugTracker.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String firstname;
	private String lastname;
	private Long totalbugs;
	@Column(unique = true)
	private String username;
	private String password;
	@OneToOne(fetch = FetchType.EAGER) // ,orphanRemoval=true TO MAKE CHANGES IN CHILD DATA
	private UserRole role;

	@ManyToMany(mappedBy = "users", fetch = FetchType.EAGER, cascade = CascadeType.ALL)

	private List<Team> teams = new ArrayList<>();

	@OneToMany
	private List<Bug> bugs = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public List<Team> getTeams() {
		return teams;
	}

	public void setTeams(List<Team> teams) {
		this.teams = teams;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<Bug> getBugs() {
		return bugs;
	}

	public void setBugs(List<Bug> bugs) {
		this.bugs = bugs;
	}

	public User() {
		super();
	}

	public Long getTotalbugs() {
		return totalbugs;
	}

	public void setTotalbugs(Long totalbugs) {
		this.totalbugs = totalbugs;
	}

	public User(Long id, String firstname, String lastname, Long totalbugs, String username, String password,
			UserRole role, List<Team> teams, List<Bug> bugs) {
		super();
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.totalbugs = totalbugs;
		this.username = username;
		this.password = password;
		this.role = role;
		this.teams = teams;
		this.bugs = bugs;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", firstname=" + firstname + ", lastname=" + lastname + ", totalbugs=" + totalbugs
				+ ", username=" + username + ", password=" + password + ", role=" + role + ", teams=" + teams
				+ ", bugs=" + bugs + "]";
	}

}
