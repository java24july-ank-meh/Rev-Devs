package com.revature.application.restControllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revature.application.beans.Greeting;
import com.revature.application.dao.EmployeeDao;
import com.revature.application.dao.beans.Employee;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

	@Autowired
	EmployeeDao employeeDAO;
	
	/*
	 * All GET requests
	 */
	@RequestMapping(path = "", method = RequestMethod.GET)
	public Set<Employee> readAllEmployees() {
		// Get all the employee from db
		return employeeDAO.readAll();
	}

	@RequestMapping(path = "/{employeeId}", method = RequestMethod.GET)
	public Employee readEmployeeById(@PathVariable long employeeId) {
		// Get single employee from db by id
		return employeeDAO.read(employeeId);
	}

	/*
	 * All POST requests
	 */
	@RequestMapping(path = "", method = RequestMethod.POST)
	public boolean createEmployee(/* Take in a location object */) {
		// Add a new user to the db
		return true;
	}

	/*
	 * All PUT request
	 */
	@RequestMapping(path = "/{userId}", method = RequestMethod.PUT)
	public boolean updateEmployee(@PathVariable long userId) {
		// Update user with userId
		return true;
	}

	/*
	 * All DELETE requests
	 */
	@RequestMapping(path = "/{userId}", method = RequestMethod.DELETE)
	public boolean deleteEmployee(@PathVariable long userId) {
		// Delete a single user
		return employeeDAO.deleteById(userId);
	}

}
