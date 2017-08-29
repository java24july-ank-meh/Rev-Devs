package com.revature.application.service;

import com.revature.application.dao.beans.Employee;

public interface SafeUserOperations {

	public void authenticateById(String id);
	public Employee loadEmployee();
//	public void saveEmployee(Employee e);//cant do without httpsession
	
}
