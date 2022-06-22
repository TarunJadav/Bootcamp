package com.BugTracker.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
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
		List<Team> teams = teamService.findAllByUsers(user);

		bug.setTester(user);
		bug.setProject(project);
		// bug.setTeams(teams);
		for (Team team2 : teams) {
			bug.setTeams(team2);
		}

		model.addAttribute("bug", bug);

		return "bug";
	}

	@PostMapping("/bug")
	public String bugReport(@RequestParam("Project") Long id, Bug bug, Principal principal, User user, Project project,
			Model model) {
		user = userService.findByUsername(principal.getName());
		project = projectService.getProjectById(id);

		bug.setTester(user);
		bug.setProject(project);

		bugService.saveBug(bug);

		Set<User> userList = new HashSet<User>();

		List<Team> teams = teamService.findAllByProjects(project);
		for (Team team : teams) {

			Set<User> user2 = userService.findAllByTeams(team);

			for (User user3 : user2) {
				String Role = "ROLE_DEVLOPER";
				UserRole userrole = user3.getRole();
				String roleuser = userrole.getRole();

				if (roleuser.equals(Role)) {

					userList.add(user3);
				}
			}

		}

		model.addAttribute("bug", bug);
		model.addAttribute("user", userList);

		return "Assignbug_team";
	}

	@GetMapping("team/adduser/{id}/{bugid}")
	public String assignBug(Model model, @PathVariable("id") Long teamid, @PathVariable("bugid") Long bugid, Team team,
			Bug bug) {

		team = teamService.getTeamById(teamid);
		bug = bugService.getBugById(bugid);

		team.getBugs().add(bug);
		bug.setTeams(team);

		bugService.saveBug(bug);

		teamService.saveTeam(team);

		return "redirect:/";
	}

	@GetMapping("/showtesterbugs")
	public String showTesterBugs(Principal principal, Model model, User user) {

		user = userService.findByUsername(principal.getName());

		List<Bug> bugsList = new ArrayList<Bug>();

		List<Team> teams = teamService.findAllByUsers(user);

		for (Team team : teams) {

			List<Bug> bug = bugService.findAllByTeams(team);

			bugsList.addAll(bug);

		}

		model.addAttribute("bug", bugsList);

		return "showBugs_Tester";

	}

}
