package com.BugTracker.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Report {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String projectname;
	private String techonology;
	private String status;
	private boolean isdeleted;
	private String startdate;

	private String end_date;

	@OneToOne
	private Project pid;

	

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

	public String getStartdate() {
		return startdate;
	}

	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	public Project getPid() {
		return pid;
	}

	public void setPid(Project pid) {
		this.pid = pid;
	}



	public Report(Long id, String projectname, String techonology, String status, boolean isdeleted, String startdate,
			String end_date, Project pid) {
		super();
		this.id = id;
		this.projectname = projectname;
		this.techonology = techonology;
		this.status = status;
		this.isdeleted = isdeleted;
		this.startdate = startdate;
		this.end_date = end_date;
		this.pid = pid;
		
	}

	public Report() {
		super();
	}

}
