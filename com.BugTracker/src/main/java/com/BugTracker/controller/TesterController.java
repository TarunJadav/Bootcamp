package com.BugTracker.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.BugTracker.entity.Bug;
import com.BugTracker.entity.Project;
import com.BugTracker.entity.Team;
import com.BugTracker.entity.User;
import com.BugTracker.entity.UserRole;
import com.BugTracker.service.BugService;
import com.BugTracker.service.ProjectService;
import com.BugTracker.service.TeamService;
import com.BugTracker.service.UserService;

@Controller
public class TesterController {

	@Autowired
	private BugService bugService;

	@Autowired
	private ProjectService projectService;

	@Autowired
	private UserService userService;

	@Autowired
	private TeamService teamService;

	@GetMapping("/project/bug/{id}")
	public String bugShow(@PathVariable Long id, Model model, Project project, User user, Principal principal, Bug bug,
			Team team) {

		project = projectService.getProjectById(id);
		user = userService.findByUsername(principal.getName());

		Set<User> userList = new HashSet<User>();

		List<Team> teams = teamService.findAllByProjects(project);
		for (Team team2 : teams) {

			Set<User> user2 = userService.findAllByTeams(team2);

			for (User user3 : user2) {
				String Role = "ROLE_DEVLOPER";
				UserRole userrole = user3.getRole();
				String roleuser = userrole.getRole();

				if (roleuser.equals(Role)) {

					userList.add(user3);
				}
			}

		}

		bug.setTester(user);
		bug.setProject(project);

		model.addAttribute("bug", bug);
		model.addAttribute("user", userList);

		return "bug";
	}

	@PostMapping("/bug")
	@Secured("ROLE_TESTER")
	@PreAuthorize("hasAuthority('ROLE_TESTER')")
	public String bugReport(@RequestParam("Project") Long id, Bug bug, Principal principal, User user, Project project,
			Model model) {
		user = userService.findByUsername(principal.getName());
		project = projectService.getProjectById(id);

		User devloper = bug.getUser();

		bug.setTester(user);
		bug.setProject(project);

		devloper.getBugs().add(bug);
		bug.setUser(devloper);

		bugService.saveBug(bug);
		userService.saveUser(devloper);

		return "redirect:/";
	}

	@GetMapping("/showtesterbugs")
	@Secured("ROLE_TESTER")
	@PreAuthorize("hasAuthority('ROLE_TESTER')")
	public String showTesterBugs(Principal principal, Model model, User user) {

		user = userService.findByUsername(principal.getName());
		List<Bug> bugsList = bugService.findAllByTester(user);
		List<Bug> list = new ArrayList<Bug>();
		for (Bug bug : bugsList) {

			if (bug.getPriority().contains("High")) {
				list.add(bug);
			}
		}

		for (Bug bug : bugsList) {

			if (bug.getPriority().contains("Average")) {
				list.add(bug);
			}
		}

		List<Bug> list2 = new ArrayList<Bug>();
		for (Bug bug : list) {

			Project project = bug.getProject();
			if (project.getStatus().contains("finished")) {
				continue;
			} else {
				list2.add(bug);

			}

		}

		model.addAttribute("bug", list2);

		return "showBugs_Tester";

	}

	@GetMapping("/bugreopen/adduser/{bugid}")
	@Secured("ROLE_TESTER")
	@PreAuthorize("hasAuthority('ROLE_TESTER')")
	public String reAssignBug(@PathVariable Long bugid) {

		Bug bug = bugService.getBugById(bugid);

		bug.setStatus("pending");
		bugService.saveBug(bug);

		return "redirect:/";
	}

	@GetMapping("/project/finished/{id}")

	public String projectFinished(@PathVariable Long id, Project project) {

		project = projectService.getProjectById(id);

		project.setStatus("finished");

		projectService.saveProject(project);
		return "redirect:/?projectfinished";
	}

}
