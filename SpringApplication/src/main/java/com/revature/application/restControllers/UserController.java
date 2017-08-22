package com.revature.application.restControllers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revature.application.beans.Greeting;

@RestController
@RequestMapping("/users")
public class UserController {

	/*
	 * All GET requests
	 */
	@RequestMapping(path = "", method = RequestMethod.GET)
	public Greeting readAllUsers() {
		// Get all the users from db
		return new Greeting(1, "Returning all users");
	}

	@RequestMapping(path = "/{userId}", method = RequestMethod.GET)
	public Greeting readUserById(@PathVariable long userId) {
		// Get single user from db by id
		return new Greeting(1, "Returning single user");
	}

	/*
	 * All POST requests
	 */
	@RequestMapping(path = "", method = RequestMethod.POST)
	public Greeting createUser(/* Take in a location object */) {
		// Add a new user to the db
		return new Greeting(1, "Added a single user");
	}

	/*
	 * All PUT request
	 */
	@RequestMapping(path = "/{userId}", method = RequestMethod.PUT)
	public Greeting updateUser(@PathVariable long userId) {
		// Update user with userId
		return new Greeting(1, "Updated user");
	}

	/*
	 * All DELETE requests
	 */
	@RequestMapping(path = "/{userId}", method = RequestMethod.DELETE)
	public Greeting deleteUser(@PathVariable long userId) {
		// Delete a single user
		return new Greeting(1, "Deleted single location");
	}

}
