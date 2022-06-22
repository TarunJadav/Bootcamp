package com.BugTracker.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class UserRole {

	@Id 

	private Long id;

	private String Role;

	public String getRole() {
		return Role;
	}

	public void setRole(String role) {
		this.Role = role;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserRole(Long id, String role) {
		super();
		this.id = id;
		this.Role = role;
	}

	public UserRole() {
		super();
	}

	@Override
	public String toString() {
		return "UserRole [id=" + id + ", Role=" + Role + "]";
	}

}
