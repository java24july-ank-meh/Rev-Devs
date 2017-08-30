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
import com.revature.application.dao.LocationDao;
import com.revature.application.dao.beans.Employee;
import com.revature.application.dao.beans.Location;
import com.revature.application.dao.beans.forms.EmployeeForm;
import com.revature.application.dao.beans.forms.LocationForm;

@RestController
@RequestMapping("/authentication")
public class LoginController {
    
    @Autowired
    EmployeeDao employeeDAO;
    
    @Autowired
	LocationDao locationDAO;
    
    /*
     * Main handler for logging in a user
     */
    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public RequestStatus login(String username, String password, HttpSession session) {
        
        // Search in the database
        Employee user = employeeDAO.read(username);
        if (user == null)
            return new RequestStatus(false, "User does not exist");
        
        if (user.getPassword().equals(password)) {
            // Start a session
            // session.setAttribute("id", ""+user.getEmployeeId());
            saveEmployee(session, user);
            
            // Return Success
            return new RequestStatus();
        } else {
            // Return Failure
            return new RequestStatus(false, "Incorrect password");
        }
    }
    
    /*
     * Main handler for logging out a user
     */
    @RequestMapping(path = "/logout", method = RequestMethod.GET)
    public RequestStatus logout(HttpSession session) {
        // Destroy session
        session.invalidate();
        
        // Return success
        return new RequestStatus();
    }
    
    private void saveEmployee(HttpSession session, Employee e) {
        session.setAttribute("id", "" + e.getEmployeeId());
    }
    
    private Employee loadEmployee(HttpSession session) {
        String id_str = (String) session.getAttribute("id");
        if (id_str == null)
            return null;
        long employee_id;
        try {
            employee_id = Long.parseLong(id_str);
        } catch (Exception e) {
            return null;
        }
        Employee employee = employeeDAO.read(employee_id);
        return employee;
    }
    
    @RequestMapping(path = "/user", method = RequestMethod.GET)
    public RequestEmployee getCurrentUser(HttpSession session) {
        Employee employee = loadEmployee(session);
        if (employee == null)
            return new RequestEmployee(false, null);
        return new RequestEmployee(true, employee);
    }
    
    @RequestMapping(path = "/update-profile", method = RequestMethod.POST)
    public RequestStatus updateProfile(String username, String email, String fname, String lname,
            HttpSession session) {
        Employee employee = loadEmployee(session);
        if (employee == null)
            return new RequestStatus(false, "Not logged in");
        
        long locId = employee.getLocation() == null ? 0 : employee.getLocation().getLocationId();
        long compId = employee.getCompany() == null ? 0 : employee.getCompany().getCompanyId();
        
        EmployeeForm employeeForm = new EmployeeForm(locId, compId, username,
                employee.getPassword(), email, fname, lname);
        
        employeeDAO.update(employee.getEmployeeId(), employeeForm);
        return new RequestStatus();
    }
    
    @RequestMapping(path = "/set-location", method = RequestMethod.POST)
    public RequestStatus setLocation(String city, HttpSession session) {
    	Employee employee = loadEmployee(session);
    	if(employee == null) {
    		 return new RequestStatus(false, "Not logged in");
    	}
    	
    	Location loc = locationDAO.read(city);
    	if(loc == null) return new RequestStatus(false, "location not found");
    	
    	employeeDAO.updateLocation(employee.getEmployeeId(), loc);
    	
    	return new RequestStatus();
    }
}
