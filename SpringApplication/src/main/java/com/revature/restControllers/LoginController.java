package com.revature.restControllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revature.beans.Greeting;

@RestController
public class LoginController {

	/*
	 * Main handler for logging in a user 
	 */
	@RequestMapping(path = "/login", method = RequestMethod.POST)
	public Greeting login() {
		// Search in the database
		
		// Start a session
		
		// Return Success or Failure
		return new Greeting(1, "Logged In");
	}
	
	/*
	 * Main handler for logging out a user
	 */
	@RequestMapping(path = "/logout", method = RequestMethod.GET)
	public Greeting logout() {
		// Destroy session 
		
		// Return success or failure
		return new Greeting(1, "Logged Out");
	}
}
