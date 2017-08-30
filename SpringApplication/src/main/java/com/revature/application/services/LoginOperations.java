package com.revature.application.services;

public interface LoginOperations {
	
    public void login(String username, String password);
	public void logout();
	public Boolean isLoggedIn();
	public Long getEmployeeId();
}
