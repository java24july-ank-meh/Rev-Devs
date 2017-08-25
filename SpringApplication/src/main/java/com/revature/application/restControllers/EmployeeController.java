package com.revature.application.restControllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revature.application.beans.RequestStatus;
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
	public List<Employee> readAllEmployees() {
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
	public RequestStatus createEmployee(@Valid Employee employee, BindingResult bindingResult) {
		// Add a new user to the db
		
		if (!bindingResult.hasErrors()) {
			employeeDAO.create(employee);
			return new RequestStatus();
		}
		return new RequestStatus(false, "Failed to create new employee");
	}

	/*
	 * All PUT request
	 */
	@RequestMapping(path = "/{userId}", method = RequestMethod.PUT)
	public RequestStatus updateEmployee(@Valid Employee employee, BindingResult bindingResult, @PathVariable long userId) {
		// Update user with userId
		
		if (!bindingResult.hasErrors()) {
			employeeDAO.update(employee);
			return new RequestStatus(); 
		}
		return new RequestStatus(false, "Failed to update employee");
	}

	/*
	 * All DELETE requests
	 */
	@RequestMapping(path = "/{userId}", method = RequestMethod.DELETE)
	public RequestStatus deleteEmployee(@PathVariable long userId) {
		// Delete a single user
		employeeDAO.deleteById(userId);
		return new RequestStatus();
	}

}
