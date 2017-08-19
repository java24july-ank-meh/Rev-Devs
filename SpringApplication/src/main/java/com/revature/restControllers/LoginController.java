package com.revature.restControllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.beans.Greeting;

@RestController
public class LoginController {

	@RequestMapping("/login")
	public Greeting login() {
		// Search in the database
		
		// Start a session
		
		// Return Success or Failure
		return new Greeting(1, "Logged In");
	}
	
	
	@RequestMapping("/logout")
	public Greeting logout() {
		// Destroy session 
		
		// Return success or failure
		return new Greeting(1, "Logged Out");
	}

}
