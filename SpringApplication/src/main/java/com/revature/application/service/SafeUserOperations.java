package com.revature.application.service;

import javax.servlet.http.HttpSession;

import com.revature.application.dao.beans.Employee;

public interface SafeUserOperations {

	public HttpSession authenticateById(HttpSession s, Employee e);
	public Employee loadEmployee();
	public void setSession(HttpSession s);
	public boolean isSessionValid(HttpSession s);
	
}
