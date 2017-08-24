package com.revature.application.restControllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revature.application.beans.Greeting;
import com.revature.application.dao.EmployeeDao;
import com.revature.application.dao.beans.Employee;

@RestController
@RequestMapping("/login")
public class LoginController {

	@Autowired
	EmployeeDao employeeDAO;
	
	/*
	 * Main handler for logging in a user
	 */
	@RequestMapping(path = "/login", method = RequestMethod.POST)
	public Greeting login(String username, String password, HttpSession session) {
		
		// Search in the database
		Employee user = employeeDAO.read(username);
		if(user == null) return new Greeting(2,"User does not exist");
		
		if(user.getPassword().equals(password)) {
			// Start a session
			session.setAttribute("id", user.getEmployeeId());
	
			// Return Success
			return new Greeting(1,"Logged in");
		} else {
			// Return Failure
			return new Greeting(3,"Incorrect password");
		}
	}

	/*
	 * Main handler for logging out a user
	 */
	@RequestMapping(path = "/logout", method = RequestMethod.GET)
	public Greeting logout(HttpSession session) {
		// Destroy session
		session.invalidate();
		
		// Return success or failure
		return new Greeting(1,"Logged out");
	}
}
