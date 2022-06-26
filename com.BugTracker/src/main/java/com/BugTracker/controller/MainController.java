package com.BugTracker.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.BugTracker.entity.Bug;
import com.BugTracker.entity.Project;
import com.BugTracker.entity.Report;
import com.BugTracker.entity.Team;
import com.BugTracker.entity.User;
import com.BugTracker.entity.UserRole;
import com.BugTracker.service.BugService;
import com.BugTracker.service.GenerateService;

import com.BugTracker.service.ProjectService;
import com.BugTracker.service.ReportService;
import com.BugTracker.service.TeamService;
import com.BugTracker.service.UserService;

/**
 * 
 * @author Tarun.Jadav Main Controller
 */

@Controller

public class MainController {

	@Autowired
	private UserService userService;

	@Autowired
	private TeamService teamService;

	@Autowired
	private ProjectService projectService;

	@Autowired
	private BugService bugService;

	@Autowired
	private ReportService reportService;

	@Autowired
	private GenerateService generateService;

	@GetMapping("/")
	public String homeShow(Principal principal, User user, Model model) {

		user = userService.findByUsername(principal.getName());
		String role = user.getRole().toString();

		if (role.contains("ROLE_ADMIN")) {

			System.out.println("admin is here");
			return "home";
		}

		if (role.contains("ROLE_TESTER")) {

			System.out.println("tester is here");
			return "testerhome";
		}
		if (role.contains("ROLE_DEVLOPER")) {

			System.out.println("devloper is here");
			return "devloperhome";
		}

		return "redirect:/logout";

	}

	@RequestMapping("/login")
	public String loginShow() {

		return "login";
	}

	@RequestMapping("/logout-success")
	public String logoutShow() {

		return "logout";
	}

	@GetMapping("/register")
	@Secured("ROLE_ADMIN")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String registerShow(Model model) {
		model.addAttribute("user", new User());
		return "register";
	}

	@PostMapping("/register")
	@Secured("ROLE_ADMIN")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String adding(User user, @RequestParam("ROLES") Long role, UserRole userRole) throws MessagingException {

		userRole.setId(role);
		user.setRole(userRole);

		userService.emailUser(user);
		userService.saveUser(user);

		return "redirect:/?success";
	}

	@GetMapping("/showuser")
	@Secured("ROLE_ADMIN")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")

	public String showUsers(Model model) {

		List<User> users = new ArrayList<User>();
		List<User> userlist = userService.getAllUsers();
		for (User user : userlist) {
			String role = user.getRole().toString();

			if (role.contains("UNASSIGN")) {
				continue;
			} else {
				users.add(user);
			}
		}

		model.addAttribute("user", users);

		return "viewusers";
	}

	// DELETE USER
	@GetMapping("/user/delete/{id}")
	@Secured("ROLE_ADMIN")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")

	public String deleteUser(@PathVariable Long id) {

		User user = userService.getUserById(id);
		List<Team> teams = teamService.findAllByUsers(user);

		User Unassignuser = userService.getUserById(1001L);

		for (int i = 0; i < teams.size(); i++) {

			Team team = teams.get(i);
			team.getUsers().remove(user);
			user.getTeams().remove(team);

			teamService.saveTeam(team);
			userService.saveUser(user);

		}

		List<Bug> bugsList = bugService.findAllByUser(user);

		for (Bug bug : bugsList) {

			bug.setTester(Unassignuser);
			bugService.saveBug(bug);
		}

		userService.deleteUserById(user.getId());

		return "redirect:/showuser";

	}

	@GetMapping("/searchuser")
	public String searchUser(@RequestParam("firstname") String firstname, Model model) {

		model.addAttribute("user", userService.findByFirstname(firstname));

		return "viewusers";
	}

	@GetMapping("/user/update/{id}")
	public String updateUser(@PathVariable Long id, Model model) {

		model.addAttribute("user", userService.getUserById(id));

		return "updateuser";
	}

	@PostMapping("/updateuser")

	public String updateUser(User user, @RequestParam("ROLES") Long role, UserRole userRole) {

		User existingUser = userService.getUserById(user.getId());

		existingUser.setId(existingUser.getId());
		existingUser.setFirstname(user.getFirstname());
		existingUser.setLastname(user.getLastname());
		userRole.setId(role);
		user.setRole(userRole);
		existingUser.setUsername(user.getUsername());
		existingUser.setRole(user.getRole());
		existingUser.setPassword(existingUser.getPassword());

		System.out.println(existingUser.getPassword());

		userService.saveUser(existingUser);

		return "redirect:/?successupdate";
	}

	// user profile
	@GetMapping("/profile")
	public String userProfile(Principal principal, Model model) {

		model.addAttribute("user", userService.findByUsername(principal.getName()));
		return "updateuser";
	}

	@GetMapping("/searchuserrole")
	public String searchByRole(@RequestParam("ROLES") Long role, Model model, User user, UserRole userRole) {

		userRole.setId(role);

		model.addAttribute("user", userService.findAllByRole(userRole));

		return "viewusers";
	}

	// user view team starts here
	@GetMapping("/showdusersteam")
	public String showTeams(Principal principal, User user, Model model) {

		user = userService.findByUsername(principal.getName());
		System.out.println(user.getId());
		model.addAttribute("team", teamService.findAllByUsers(user));

		return "viewteams_devloper";
	}

	@GetMapping("team/viewteams/{id}")
	public String userViewTeamUser(@PathVariable Long id, Team team, Model model) {
		model.addAttribute("team", teamService.getTeamById(id));
		model.addAttribute("user", userService.findAllByTeams(team));
		return "devloper_view_teamuser.html";
	}

	// tester view Project here

	@GetMapping("/showuserproject")
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
				if (project2.getStatus().contains("finished")) {
					continue;
				} else {
					proList.add(project2);
				}
			}

		}
		model.addAttribute("project", proList);

		return "view_projectuser";
	}

	@GetMapping("showallproject")
	@Secured("ROLE_ADMIN")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String showAllProjects(Model model) {

		List<Project> project = projectService.getAllProjects();
		List<Project> prList = new ArrayList<Project>();

		for (Project project2 : project) {
			if (project2.getStatus().contains("unassigned")) {

				continue;
			} else {
				prList.add(project2);
			}
		}

		model.addAttribute("project", prList);

		return "admin_showallproject";
	}

	@GetMapping("/project/report/{id}")
	public String reportAdd(Model model, @PathVariable Long id, Report report) {

		String currentDate = java.time.LocalDate.now().toString();

		Project project = projectService.getProjectById(id);

		List<Bug> bugs = bugService.findAllByProjects(project);
		Long totalBugs = (long) bugs.size();

		report.setIsdeleted(project.isIsdeleted());
		report.setPid(project);
		report.setProjectname(project.getProjectname());
		report.setStartdate(project.getStartdate());
		report.setStatus(project.getStatus());
		report.setTechonology(project.getTechonology());
		report.setEnd_date(currentDate);
		report.setTotalbugs(totalBugs);

		model.addAttribute("project", report);
		return "report";
	}

	@PostMapping("/report")
	public String reportGenerate(Report report, Project project) {
		project = report.getPid();

		reportService.saveReport(report);

		return "redirect:/";
	}

	@GetMapping("/team/viewteamsProject/{id}")
	public String showTeamsProject(@PathVariable Long id, Model model, Team team) {

		team = teamService.getTeamById(id);

		List<Project> projects = projectService.findAllByTeams(team);
		model.addAttribute("project", projects);

		return "teams_projects";
	}

	@GetMapping("/showallreport")
	public String showAllReport(Model model) {

		

		model.addAttribute("report", reportService.findAllReport());

		return "showAllReport";

	}

	// ADMIN SHOW ALL BUGS
	@GetMapping("/project/adminShowAllBug/{id}")
	public String ShowAllBugsProject(@PathVariable Long id, Model model, Project project) {

		project = projectService.getProjectById(id);
		List<Bug> bugs = bugService.findAllByProjects(project);
		model.addAttribute("bug", bugs);

		return "admin_showProjectBugs";
	}

	// DOWNLOAD AS A PDF

	@GetMapping("project/download/{id}")
	public String downloadReportPdf(@PathVariable Long id, Report report) {

		report = reportService.getById(id);
		String str = report.getProjectname();

		String projectName = str.replaceAll("\\s", ""); // using built in method

		Map<String, Object> data = new HashMap<>();
		data.put("report", report);
		generateService.generatePdfFile("pdfReport", data, projectName + ".pdf");

		System.out.println("ho gayaaa bhaii");

		return "redirect:/";
	}

	@GetMapping("project/downloadExcel/{id}")
	public String dowloadReportExcel(@PathVariable Long id, Report report) {

		report = reportService.getById(id);
		String str = report.getProjectname();

		String projectName = str.replaceAll("\\s", ""); // using built in method

		Map<String, Object> data = new HashMap<>();
		data.put("report", report);
		generateService.generateExcelFile("pdfReport", data, projectName + ".xlsx");

		System.out.println("ho gayaaa bhaii");

		return "redirect:/";
	}

}
