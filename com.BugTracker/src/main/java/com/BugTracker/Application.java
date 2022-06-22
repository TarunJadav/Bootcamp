package com.BugTracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * 
 * @author Tarun.Jadav
 *@version 0.0.1
 *@since 2022
 *{@summary
 *
 *Name:Bug Tracker
 *Roles:Admin,Devloper,Tester
 *Features:Devloper Crud ,Tester Crud,Bug Report,Project Report
 *Dependencis:spring-data-jpa, javamail, sprig-security,thymleaf,spring-web,spring-devtools,mysql 
 *
 * A Bug Tracking website where a devloper can upload there projects and tester can test them and find bugs and make a report abaout bugs and assign them to a devloper
 * 
 * }
 *
 */


@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
