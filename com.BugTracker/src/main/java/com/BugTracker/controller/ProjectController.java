package com.BugTracker.controller;

import java.util.ArrayList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.BugTracker.entity.Bug;
import com.BugTracker.entity.Project;
import com.BugTracker.entity.Report;
import com.BugTracker.entity.Team;
import com.BugTracker.repository.ReportRepository;
import com.BugTracker.service.BugService;
import com.BugTracker.service.ProjectService;
import com.BugTracker.service.TeamService;

@Controller
public class ProjectController {

	@Autowired
	private ProjectService projectService;

	@Autowired
	private TeamService teamService;

	@Autowired
	private BugService bugService;

	@Autowired
	private ReportRepository reportRepository;

	@GetMapping("/addproject")
	@Secured("ROLE_ADMIN")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String project(Model model) {

		model.addAttribute("project", new Project());

		return "addproject";
	}

	// CREATE PROJECT
	@PostMapping("/addproject")
	@Secured("ROLE_ADMIN")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String addProject(Project project) {

		String currentDate = java.time.LocalDate.now().toString();

		System.out.println(java.time.LocalDate.now());

		project.setStartdate(currentDate);
		project.setIsdeleted(false);

		projectService.saveProject(project);

		return "home";
	}

	// SHOW PROJECTS
	@GetMapping("/showproject")
	public String showProjects(Model model) {

		List<Project> projects = new ArrayList<>();

		List<Project> proList = projectService.getAllProjects();
		for (Project project : proList) {

			String projectname = project.getProjectname().toString();
			if (projectname.contains("unassigned")) {
				continue;
			} else {

				Report report = reportRepository.findByPid(project);
				if (report == null) {
					projects.add(project);
				}
			}
		}

		model.addAttribute("project", projects);

		return "project_View";
	}

	// ASSIGN SHOW
	@GetMapping("/project/addteam/{id}")
	@Secured("ROLE_ADMIN")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String showAvailableTeams(@PathVariable Long id, Model model, Project project) {

		project = projectService.getProjectById(id);

		List<Team> teams = teamService.getAllTeams();
		List<Team> projectTeams = teamService.findAllByProjects(project);

		for (int i = 0; i < projectTeams.size(); i++) {

			Team team = projectTeams.get(i);
			teams.remove(team);
		}

		model.addAttribute("project", projectService.getProjectById(id));
		model.addAttribute("team", teams);

		return "assignproject";
	}

	// ADD TEAMS IN PROJECT OR ASSIGN TEAMS

	@GetMapping("project/addteam/team/add/{id}/{projectid}")
	@Secured("ROLE_ADMIN")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String assignTeam(@PathVariable("id") Long id, @PathVariable("projectid") Long projectid, Project project,
			Team team) {

		team = teamService.getTeamById(id);
		project = projectService.getProjectById(projectid);

		project.getTeams().add(team);
		team.getProjects().add(project);

		List<Project> projects = projectService.findAllByTeams(team);
		if (projects != null) {

			for (Project project2 : projects) {

				if (project2.getTeams().equals(project.getTeams())) {

					return "redirect:/showproject?exist";
				}
			}

		}

		projectService.saveProject(project);
		teamService.saveTeam(team);

		return "redirect:/showproject?added";
	}

	// DELETE PROJECT
	@GetMapping("/project/delete/{id}")
	@Secured("ROLE_ADMIN")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")

	public String deleteProject(@PathVariable Long id, Project project) {

		project = projectService.getProjectById(id);
		Long unassignProjectId = 1001L;
		Project unassignProject = projectService.getProjectById(unassignProjectId);

		List<Bug> bugs = bugService.findAllByProjects(project);

		for (Bug bug : bugs) {

			bug.setProject(unassignProject);

			bugService.saveBug(bug);
		}

		projectService.deleteProjectById(id);

		return "redirect:/showproject?delete";
	}

	@GetMapping("/project/viewteam/{id}")

	public String viewAssignedTeams(@PathVariable Long id, Project project, Model model) {

		project = projectService.getProjectById(id);

		model.addAttribute("project", projectService.getProjectById(id));
		model.addAttribute("team", teamService.findAllByProjects(project));

		return "view_assigndteams";
	}

	@GetMapping("/project/viewteam/team/remove/{id}/{teamid}")
	@Secured("ROLE_ADMIN")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String removeTeams(@PathVariable("id") Long projectid, @PathVariable Long teamid, Project project,
			Team team) {

		project = projectService.getProjectById(projectid);
		team = teamService.getTeamById(teamid);

		project.getTeams().remove(team);
		team.getProjects().remove(project);

		projectService.saveProject(project);
		teamService.saveTeam(team);

		return "redirect:/showproject?remove";

	}

	@GetMapping("/completedprojects")
	public String completedProjects(Model model) {
		List<Project> proList = projectService.getAllProjects();
		List<Project> projects = new ArrayList<Project>();

		for (Project project : proList) {

			if (project.getStatus().contains("finished")) {

				if (project != null) {

					Report report = reportRepository.findByPid(project);
					if (report != null) {

						continue;

					} else {
						projects.add(project);

					}
				}

			}
		}

		model.addAttribute("project", projects);

		return "completedProject";
	}

	// PROJECT RE-OPEN
	@GetMapping("/project/reopenProject/{id}")
	public String reOpenProject(@PathVariable Long id, Model model, Project project) {
		project = projectService.getProjectById(id);
		project.setStatus("pending");
		projectService.saveProject(project);

		return "redirect:/";
	}

}
