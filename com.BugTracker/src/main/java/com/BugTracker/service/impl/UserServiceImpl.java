package com.BugTracker.service.impl;

import java.util.List;
import java.util.Set;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.BugTracker.entity.Team;
import com.BugTracker.entity.User;
import com.BugTracker.entity.UserRole;
import com.BugTracker.principle.UserPrinciple;
import com.BugTracker.repository.UserRepository;
import com.BugTracker.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JavaMailSender javaMailSender;

	public UserServiceImpl(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;

	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userRepository.findByUsername(username);

		if (user == null) {

			throw new UsernameNotFoundException("user 404");
		}

		return new UserPrinciple(user);
	}

	@Override
	public User saveUser(User user) {

		return userRepository.save(user);
	}

	@Override
	public User findByUsername(String username) {

		return userRepository.findByUsername(username);
	}

	@Override
	public List<User> getAllUsers() {

		return userRepository.findAll();
	}

	@Override
	public User getUserById(Long id) {

		return userRepository.findById(id).get();
	}

	@Override
	public void deleteUserById(Long id) {

		userRepository.deleteById(id);
	}

	@Override
	public List<User> findByFirstname(String firstname) {

		return userRepository.findByFirstname(firstname);
	}

	@Override
	public List<User> findAllByRole(UserRole role) {

		return userRepository.findAllByRole(role);
	}

	@Override
	public void emailUser(User user) throws MessagingException {

		MimeMessage msg = javaMailSender.createMimeMessage();

		try {

			MimeMessageHelper helper = new MimeMessageHelper(msg, true);
			helper.setTo(user.getUsername());

			helper.setSubject("Testing from Spring Boot");

			helper.setText("<h1>Check attachment for image!</h1>" + "<h3> your username is :" + user.getUsername()
					+ "<br>and your password is: " + user.getPassword() + "</h3>", true);

			helper.addAttachment("my_photo.png", new ClassPathResource("android.png"));

			javaMailSender.send(msg);
			System.out.println(" email done");

		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	@Override
	public Set<User> findAllByTeams(Team teams) {

		return userRepository.findAllByTeams(teams);
	}

	

}
