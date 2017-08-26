package com.revature.application.dao;

import java.util.List;

import com.revature.application.dao.beans.Company;
import com.revature.application.dao.beans.forms.CompanyForm;

public interface CompanyDao {
	public boolean create(CompanyForm companyForm);
	public Company read(long company_id);
	public List<Company> readAll();
	public boolean update(long company_id, CompanyForm company);
	public boolean delete(Company company);
	public boolean deleteById(long company_id);
}
