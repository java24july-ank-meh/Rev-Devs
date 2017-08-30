package com.revature.application.restControllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revature.application.beans.RequestStatus;
import com.revature.application.dao.EmployeeDao;
import com.revature.application.dao.beans.Employee;
import com.revature.application.dao.beans.forms.EmployeeForm;
import com.revature.application.services.LoginOperations;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    
    @Autowired
    EmployeeDao employeeDAO;
    
    @Autowired
    LoginOperations loginService;
    
    /*
     * All GET requests
     */
    @RequestMapping(path = "", method = RequestMethod.GET)
    public ResponseEntity<List<Employee>> readAllEmployees() {
        // Get all the employee from db
        if (loginService.isLoggedIn()) {
            return new ResponseEntity<>(employeeDAO.readAll(), HttpStatus.OK);
        } else {
           return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
    
    @RequestMapping(path = "/{employeeId}", method = RequestMethod.GET)
    public ResponseEntity<Employee> readEmployeeById(@PathVariable long employeeId) {
        // Get single employee from db by id
        if (loginService.isLoggedIn()) {
            return new ResponseEntity<>(employeeDAO.read(employeeId), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
    
    /*
     * All POST requests
     */
    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<RequestStatus> createEmployee(@Valid EmployeeForm employeeForm,
            BindingResult bindingResult) {
        // Add a new user to the db
        if (loginService.isLoggedIn()) {
            if (!bindingResult.hasErrors()) {
                employeeDAO.create(employeeForm);
                return new ResponseEntity<>(new RequestStatus(), HttpStatus.OK);
            }
            return new ResponseEntity<>(new RequestStatus(false, "Failed to create new employee"),
                    HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
    
    /*
     * All PUT request
     */
    @RequestMapping(path = "/{userId}", method = RequestMethod.PUT)
    public ResponseEntity<RequestStatus> updateEmployee(@Valid EmployeeForm employeeForm,
            BindingResult bindingResult, @PathVariable long userId) {
        // Update user with userId
        
        if (loginService.isLoggedIn()) {
            if (!bindingResult.hasErrors()) {
                employeeDAO.update(userId, employeeForm);
                return new ResponseEntity<>(new RequestStatus(), HttpStatus.OK);
            }
            return new ResponseEntity<>(new RequestStatus(false, "Failed to update new employee"),
                    HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
    
    /*
     * All DELETE requests
     */
    @RequestMapping(path = "/{userId}", method = RequestMethod.DELETE)
    public ResponseEntity<RequestStatus> deleteEmployee(@PathVariable long userId) {
        // Delete a single user
        if (loginService.isLoggedIn()) {
            employeeDAO.deleteById(userId);
            return new ResponseEntity<>(new RequestStatus(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

    }
    
}
