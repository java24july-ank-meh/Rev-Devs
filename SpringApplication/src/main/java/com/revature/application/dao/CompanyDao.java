package com.revature.application.dao;

import java.util.Set;

import com.revature.application.dao.beans.*;

public interface CompanyDao {
	public boolean create(Company company);
	public Company read(long company_id);
	public Set<Company> readAll();
	public boolean update(Company company);
	public boolean delete(Company company);
	public boolean deleteById(long company_id);
}
