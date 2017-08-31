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
        if(employee == null) return;
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
    	 System.out.println("------------------------login validation");
        String employeeId = (String) masterSession.getAttribute("id");
        
        if (!employeeId.equals(null)) {
        	 System.out.println("-----------------------not null");
            return true;
        } else {
        	 System.out.println("------------------------null");
            return false;
        }
    }

    @Override
    public Long getEmployeeId() {
       String empid = (String) masterSession.getAttribute("id");
       if(!empid.equals(null))return Long.parseLong(empid);
       return null;
    }

}
