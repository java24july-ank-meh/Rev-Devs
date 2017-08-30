package com.revature.application.services;

import com.revature.application.dao.beans.Employee;

public interface SafeUserOperations {

	//checks login credentials
	public boolean checkLoginCredits(String un, String pw);
	
	//logout
	public void logoutUser();
	
	//authenticating user
	public void authenticateById(Long id);
	
	//if user needs to be loaded from master session
	public Employee loadEmployee();
	
	//checks if authenticated or not and have employee id
	public Boolean isValidSession();
	
	//update user taken from out of controller
	public void updateUser(Employee e2);
	public void updateUser2(String username, String email, String fname, String lname);
	public boolean setLocation(String city);
	
	
}
