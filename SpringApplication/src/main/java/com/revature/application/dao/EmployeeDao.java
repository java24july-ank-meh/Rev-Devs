package com.revature.application.dao;

import java.util.List;

import com.revature.application.dao.beans.Company;
import com.revature.application.dao.beans.Employee;
import com.revature.application.dao.beans.Location;
import com.revature.application.dao.beans.forms.EmployeeForm;

public interface EmployeeDao {
	public boolean create(EmployeeForm employeeForm);
	public Employee read(long employee_id);
	public Employee read(String username);
	public List<Employee> readAll();
	public boolean update(long employee_id, EmployeeForm employeeForm);
	public boolean updateLocation(long employee_id, Location location);
	public boolean updateCompany(long employee_id, Company company);
	public boolean delete(Employee emp);
	public boolean deleteById(long employee_id);
}
