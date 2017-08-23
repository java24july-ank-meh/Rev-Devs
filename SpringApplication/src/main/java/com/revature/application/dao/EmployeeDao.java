package com.revature.application.dao;

import com.revature.application.dao.beans.*;

public interface EmployeeDao {
	public boolean create(Employee emp);
	public Employee read(long employee_id);
	public Employee read(String username);
	public boolean update(Employee emp);
	public boolean delete(Employee emp);
}
