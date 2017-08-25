package com.revature.application.restControllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revature.application.beans.Greeting;
import com.revature.application.beans.RequestEmployee;
import com.revature.application.beans.RequestStatus;
import com.revature.application.dao.EmployeeDao;
import com.revature.application.dao.beans.Employee;

@RestController
@RequestMapping("/authentication")
public class LoginController {

	@Autowired
	EmployeeDao employeeDAO;
	
	/*
	 * Main handler for logging in a user
	 */
	@RequestMapping(path = "/login", method = RequestMethod.POST)
	public RequestStatus login(String username, String password, HttpSession session) {
		
		// Search in the database
		Employee user = employeeDAO.read(username);
		if(user == null) return new RequestStatus(false,"User does not exist");
		
		if(user.getPassword().equals(password)) {
			// Start a session
			session.setAttribute("username", user.getUsername());
	
			// Return Success
			return new RequestStatus();
		} else {
			// Return Failure
			return new RequestStatus(false,"Incorrect password");
		}
	}

	/*
	 * Main handler for logging out a user
	 */
	@RequestMapping(path = "/logout", method = RequestMethod.GET)
	public RequestStatus logout(HttpSession session) {
		// Destroy session
		session.invalidate();
		
		// Return success or failure
		return new RequestStatus();
	}
	
	@RequestMapping(path = "/user", method = RequestMethod.GET)
	public RequestEmployee getCurrentUser(HttpSession session) {
		String username = (String)session.getAttribute("username");
		if(username == null) return new RequestEmployee(false,null);
		Employee employee = employeeDAO.read(username);
		if(employee == null) return new RequestEmployee(false,null);
		return new RequestEmployee(true,employee);
	}
}
