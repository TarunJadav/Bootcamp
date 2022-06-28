package com.BugTracker.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Iterator;
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
	@Secured("ROLE_DEVLOPER")
	@PreAuthorize("hasAuthority('ROLE_DEVLOPER')")
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

	// DEVLOPER BUG VIEW
	@GetMapping("/viewbugs")
	@Secured("ROLE_DEVLOPER")
	@PreAuthorize("hasAuthority('ROLE_DEVLOPER')")
	public String viewBugs(Principal principal, User user, Team team, Bug bug, Model model) {

		user = userService.findByUsername(principal.getName());

		List<Bug> bugList = bugService.findAllByUser(user);
		List<Bug> list = new ArrayList<Bug>();

		List<Bug> list2 = new ArrayList<Bug>();

		for (Bug bug2 : bugList) {
			if (bug2.getPriority().contains("High")) {
				list.add(bug2);
			}
		}

		for (Bug bug2 : bugList) {
			if (bug2.getPriority().contains("Average")) {
				list.add(bug2);
			}
		}

		Iterator<Bug> itr = list.iterator();
		while (itr.hasNext()) {
			Bug bug2 = (Bug) itr.next();
			if (bug2.getStatus().contains("solved")) {
				continue;
			} else {
				Project project = bug2.getProject();
				if (project.getStatus().contains("finished")) {
					continue;
				} else {
					if (bug2.getStatus().contains("bugdone")) {
						continue;
					}

					list2.add(bug2);
				}
			}

		}

		model.addAttribute("bug", list2);

		return "viewbugs_devloper";
	}

	@GetMapping("/project/solvebug/{id}")
	@Secured("ROLE_DEVLOPER")
	@PreAuthorize("hasAuthority('ROLE_DEVLOPER')")
	public String solveBug(@PathVariable Long id, Bug bug, Model model) {

		System.out.println(id);
		bug = bugService.getBugById(id);
		bug.setStatus("solved");

		bugService.saveBug(bug);

		return "redirect:/";
	}

	@PostMapping("/bugsolve")
	@Secured("ROLE_DEVLOPER")
	@PreAuthorize("hasAuthority('ROLE_DEVLOPER')")
	public String solveBug(Bug bug) {

		Bug existingBug = bugService.getBugById(bug.getId());

		existingBug.setStatus(bug.getStatus());

		bugService.saveBug(existingBug);

		return "redirect:/";
	}

}
