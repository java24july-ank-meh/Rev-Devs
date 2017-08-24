package com.revature.application.dao;

import java.util.List;

import com.revature.application.dao.beans.*;

public interface EmployeeDao {
	public boolean create(Employee emp);
	public Employee read(long employee_id);
	public Employee read(String username);
	public List<Employee> readAll();
	public boolean update(Employee emp);
	public boolean delete(Employee emp);
	public boolean deleteById(long employee_id);
}
