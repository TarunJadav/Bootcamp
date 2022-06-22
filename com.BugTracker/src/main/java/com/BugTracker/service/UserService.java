package com.BugTracker.service;

import java.util.List;
import java.util.Set;

import javax.mail.MessagingException;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.BugTracker.entity.Team;
import com.BugTracker.entity.User;
import com.BugTracker.entity.UserRole;


public interface UserService  extends UserDetailsService{

	
	User saveUser(User user);
	
	User findByUsername(String username);
	
	List<User> getAllUsers();
	
	User getUserById(Long id);
	
	void deleteUserById(Long id);
	
	List<User> findByFirstname(String firstname);
	
	List<User> 	findAllByRole(UserRole role);
	
	void emailUser(User user) throws MessagingException;
	
	Set<User> findAllByTeams(Team teams);
	
	

	
	

	
	
	
	
	
	
	
	
}
