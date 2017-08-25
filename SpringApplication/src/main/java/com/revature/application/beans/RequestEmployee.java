package com.revature.application.beans;

import com.revature.application.dao.beans.Employee;

public class RequestEmployee {

	private boolean success;
	private Employee employee;
	
	public RequestEmployee(boolean success, Employee employee) {
		this.success = success;
		this.employee = employee;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
}
