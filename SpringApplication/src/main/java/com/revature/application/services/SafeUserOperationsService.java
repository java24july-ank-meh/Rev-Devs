package com.revature.application.services;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.application.dao.EmployeeDao;
import com.revature.application.dao.LocationDao;
import com.revature.application.dao.beans.Employee;
import com.revature.application.dao.beans.Location;
import com.revature.application.dao.beans.forms.EmployeeForm;

@Service
public class SafeUserOperationsService implements SafeUserOperations{

	@Autowired
	HttpSession masterSession;
	@Autowired
	EmployeeDao employeeDAO;
	@Autowired
	LocationDao locationDAO;

	@Override
	public boolean checkLoginCredits(String username, String password) {

		Employee user = employeeDAO.read(username);

		if (user == null) return false;

		if(user.getPassword().equals(password)) {
			authenticateById(user.getEmployeeId());
		}

		return false;
	}

	public void logoutUser() {

		masterSession.setAttribute("id", null);	
		masterSession.setAttribute("auth", false);

		masterSession.invalidate();
	}

	@Override
	public void authenticateById(Long id) {

		masterSession.setAttribute("id", "" + id);	
		masterSession.setAttribute("auth", true);
	}

	@Override
	public Employee loadEmployee() {
		

		Long emp_id = (Long) masterSession.getAttribute("id");

		if(emp_id == null) return null;
		//^shouldnt happen but placed for precaution
		Employee emp = employeeDAO.read(emp_id);

		return emp;
	}

	@Override
	public Boolean isValidSession() {
	    
	    Boolean isAuthenticated = (Boolean) masterSession.getAttribute("auth");
	    if (isAuthenticated != null) {
	        return isAuthenticated;
	    } else {
	        return false;
	    }
	}

	@Override
	public void updateUser(Employee e2) {
		Employee emp = loadEmployee();
		if(emp == null)return;

		long locId = emp.getLocation().getLocationId();
		long compId = emp.getCompany().getCompanyId();

		EmployeeForm employeeForm = new EmployeeForm(locId, compId, e2.getUsername(),
				e2.getPassword(), e2.getEmail(), e2.getFname(), e2.getLname());

		employeeDAO.update(emp.getEmployeeId(), employeeForm);
	}

	@Override
	public void updateUser2(String username, String email, String fname, String lname) {
		Employee emp = loadEmployee();
		if(emp == null)return;
		
		long locId = emp.getLocation() == null ? 0 : emp.getLocation().getLocationId();
		long compId = emp.getCompany() == null ? 0 : emp.getCompany().getCompanyId();

		EmployeeForm employeeForm = new EmployeeForm(locId, compId, username,
				emp.getPassword(), email, fname, lname);

		employeeDAO.update(emp.getEmployeeId(), employeeForm);
	}

	@Override
	public boolean setLocation(String city) {
		Employee emp = loadEmployee();
		if(emp == null)return false;
		Location loc = null;
		loc = locationDAO.read(city);
		if(loc == null)return false;

		emp.setLocation(loc);

		EmployeeForm employeeForm = new EmployeeForm(loc.getLocationId(), emp.getCompany().getCompanyId(), emp.getUsername(),
				emp.getPassword(), emp.getEmail(), emp.getFname(), emp.getLname());
		employeeDAO.update(emp.getEmployeeId(), employeeForm);
		return true;	
	}

}
