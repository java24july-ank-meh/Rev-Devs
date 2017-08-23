package com.revature.application.dao.implementations;

import java.util.Set;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.revature.application.dao.CompanyDao;
import com.revature.application.dao.beans.Company;

public class CompanyDaoImpl implements CompanyDao {

	@Autowired
	SessionFactory sf;
	
	
	@Override
	@Transactional
	public boolean create(Company company) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Company read(long company_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Company> readAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean update(Company company) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Company company) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteById(long company_id) {
		// TODO Auto-generated method stub
		return false;
	}



}
