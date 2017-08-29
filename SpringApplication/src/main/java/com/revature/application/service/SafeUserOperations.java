package com.revature.application.service;

import javax.servlet.http.HttpSession;

import com.revature.application.dao.beans.Employee;

public interface SafeUserOperations {

	public void authenticateById(String id);
	public Employee loadEmployee();
	public void saveEmployee(Employee e, HttpSession s);//cant do without httpsession
	public void setSession(HttpSession s);
	public boolean isSessionValid(HttpSession s);
	
}
