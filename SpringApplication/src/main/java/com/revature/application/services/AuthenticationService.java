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
public class AuthenticationService implements LoginOperations {

	@Autowired
	HttpSession masterSession;
	@Autowired
	EmployeeDao employeeDAO;

    @Override
    public void login(String username, String password) {

        Employee employee = employeeDAO.read(username);
        if (employee.getPassword().equals(password)) {
            masterSession.setAttribute("id", employee.getEmployeeId());
        }
    }

    @Override
    public void logout() {
//        masterSession.removeAttribute("id");
        masterSession.setAttribute("id", null);
    }

    @Override
    public Boolean isLoggedIn() {
        
        Long employeeId = (Long) masterSession.getAttribute("id");
        if (employeeId != null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Long getEmployeeId() {
       return (Long) masterSession.getAttribute("id");
    }

}
