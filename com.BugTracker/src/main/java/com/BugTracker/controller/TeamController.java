package com.BugTracker.controller;

import java.util.ArrayList;
import java.util.Iterator;
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
import org.springframework.web.bind.annotation.RequestMapping;

import com.BugTracker.entity.Project;
import com.BugTracker.entity.Team;
import com.BugTracker.entity.User;
import com.BugTracker.entity.UserRole;
import com.BugTracker.service.ProjectService;
import com.BugTracker.service.TeamService;
import com.BugTracker.service.UserService;

@Controller
public class TeamController {

	@Autowired
	private TeamService teamService;

	@Autowired
	private UserService userService;

	@Autowired
	private ProjectService projectService;

	@GetMapping("/addteam")
	@Secured("ROLE_ADMIN")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")

	public String showTeamReg() {

		return "team";
	}

	@PostMapping("/addteam")
	@Secured("ROLE_ADMIN")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")

	public String addTeam(Team team) {

		teamService.saveTeam(team);
		return "redirect:/?team";
	}

	@GetMapping("/showteam")
	@Secured("ROLE_ADMIN")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")

	public String showTeams(Model model) {

		model.addAttribute("team", teamService.getAllTeams());

		return "showteam";

	}

	@GetMapping("team/adduser/{id}")
	@Secured("ROLE_ADMIN")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")

	public String teamAddUser(Model model, @PathVariable Long id, Team team) {

		team = teamService.getTeamById(id);
		String teamrole = team.getTeamrole();

		List<User> users = userService.getAllUsers();

		Set<User> teamUser = userService.findAllByTeams(team);
		List<User> liUsers = new ArrayList<User>();

		List<User> liuseUsers2 = new ArrayList<User>();

		System.out.println(users.size());

		System.out.println(teamUser.size());

		for (User user : users) {
			String role = user.getRole().toString();
			if (role.contains("UNASSIGN")) {

				continue;
			} else {
				liUsers.add(user);
			}
		}

		Iterator<User> itr = teamUser.iterator();
		while (itr.hasNext()) {
			User teamuser2 = itr.next();
			liUsers.remove(teamuser2);
		}
		for (User user : liUsers) {
			UserRole userrole = user.getRole();
			String userrole2 = userrole.getRole();
			if (userrole2.contains(teamrole)) {

				liuseUsers2.add(user);

			}
		}

		model.addAttribute("team", teamService.getTeamById(id));
		model.addAttribute("user", liuseUsers2);

		return "addteams_user";
	}

	@RequestMapping("team/adduser/user/addteam/{id}/{teamid}")
	@Secured("ROLE_ADMIN")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")

	public String addTeamMember(@PathVariable("id") Long id, @PathVariable("teamid") Long teamid, Team team,
			User user) {

		team = teamService.getTeamById(teamid);
		user = userService.getUserById(id);

		team.getUsers().add(user);
		user.getTeams().add(team);

		teamService.saveTeam(team);
		userService.saveUser(user);

		return "redirect:/showteam?added";
	}

	@RequestMapping("team/viewteam/user/remove/{id}/{teamid}")
	@Secured("ROLE_ADMIN")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")

	public String removeTeamMember(@PathVariable("id") Long id, @PathVariable("teamid") Long teamid, Team team,
			User user) {

		team = teamService.getTeamById(teamid);
		user = userService.getUserById(id);

		team.getUsers().remove(user);
		user.getTeams().remove(team);

		teamService.saveTeam(team);
		userService.saveUser(user);

		return "redirect:/showteam?remove";
	}

	@GetMapping("/team/delete/{id}")
	@Secured("ROLE_ADMIN")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")

	// CHANGES REQUIRED HERE
	public String deleteTeam(@PathVariable Long id) {

		Team team = teamService.getTeamById(id);

		List<Project> projects = projectService.findAllByTeams(team);

		for (int i = 0; i < projects.size(); i++) {
			Project project = projects.get(i);

			project.getTeams().remove(team);
			team.getProjects().remove(project);

			projectService.saveProject(project);
			teamService.saveTeam(team);

		}

		teamService.deleteTeamById(id);

		return "redirect:/showteam?delete";
	}

	@GetMapping("/team/viewteam/{id}")
	@Secured("ROLE_ADMIN")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")

	public String viewTeamMembers(@PathVariable Long id, Team team, Model model) {

		System.out.println(id);
		team = teamService.getTeamById(id);

		model.addAttribute("team", teamService.getTeamById(id));
		model.addAttribute("user", userService.findAllByTeams(team));

		return "view_teamusers";
	}

	@GetMapping("/project/finished/{id}")

	public String projectFinished(@PathVariable Long id, Project project) {

		project=projectService.getProjectById(id);
		
		project.setStatus("finished");

		projectService.saveProject(project);
		return "redirect:/?projectfinished";
	}

}
