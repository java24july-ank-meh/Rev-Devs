package com.revature.application.services;

import javax.servlet.http.HttpSession;

import com.revature.application.dao.beans.Employee;

public interface SafeUserOperations {

	//checks login credentials
	public boolean checkLoginCredits(String un, String pw);
	
	//authenticating user
	public HttpSession authenticateById(Long id);
	
	//if user needs to be loaded from master session
	public Employee loadEmployee();
	
	//checks if authenticated or not and have employee id
	public boolean isSessionValid();
	
	//update user taken from out of controller
	public void updateUser(Employee e2);
	
}
