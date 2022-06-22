package com.BugTracker.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.BugTracker.entity.Bug;
import com.BugTracker.entity.Project;
import com.BugTracker.entity.Team;
import com.BugTracker.entity.User;
import com.BugTracker.service.BugService;
import com.BugTracker.service.ProjectService;
import com.BugTracker.service.TeamService;
import com.BugTracker.service.UserService;

@Controller
public class DevloperController {

	@Autowired
	private ProjectService projectService;

	@Autowired
	private UserService userService;

	@Autowired
	private TeamService teamService;

	@Autowired
	private BugService bugService;

	// user view Project here

	@GetMapping("/showdevloperproject")
	public String showProjects(Principal principal, User user, Model model, Team team, Project project) {

		List<Team> teams = new ArrayList<Team>();
		List<Project> projects = new ArrayList<Project>();
		List<Project> proList = new ArrayList<Project>();

		user = userService.findByUsername(principal.getName());
		teams = teamService.findAllByUsers(user);

		for (Team team2 : teams) {

			System.out.println(team2);
			projects = projectService.findAllByTeams(team2);

			for (Project project2 : projects) {
				System.out.println(project2);
				proList.add(project2);
			}

		}
		model.addAttribute("project", proList);

		return "showdevloperproject";
	}

	@GetMapping("/viewbugs")
	public String viewBugs(Principal principal, User user, Team team, Bug bug, Model model) {

		List<Team> teams = new ArrayList<>();
		List<Bug> bugs = new ArrayList<>();
		List<Bug> bugList = new ArrayList<>();

		user = userService.findByUsername(principal.getName());
		teams = teamService.findAllByUsers(user);

		for (Team team2 : teams) {
			bugs = bugService.findAllByTeams(team2);
			for (Bug bug2 : bugs) {
              
				if (bug2.getStatus().contains("solved")) {
					continue;
				}else {
					bugList.add(bug2);
				}
				
				

			}

		}

		model.addAttribute("bug", bugList);

		return "viewbugs_devloper";
	}

	@GetMapping("/project/solvebug/{id}")
	public String solveBug(@PathVariable Long id, Bug bug, Model model) {

		
		System.out.println(id);
		bug = bugService.getBugById(id);
		model.addAttribute("bug", bug);

		return "devloper_solvebug";
	}
	@PostMapping("/bugsolve")
	public String solveBug(Bug bug) {
		
		Bug existingBug=bugService.getBugById(bug.getId());
		
		
		existingBug.setStatus(bug.getStatus());
		
		bugService.saveBug(existingBug);		
		
		return "redirect:/";
	}
	

}
