package com.revature.application.dao.implementations;

import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.revature.application.dao.CompanyDao;
import com.revature.application.dao.beans.Company;

public class CompanyDaoImpl implements CompanyDao {

	@Autowired
	SessionFactory sf;
	
	@Autowired
	CriteriaBuilder builder;
	
	@Override
	@Transactional
	public boolean create(Company company) {
	
		Session session = sf.openSession();
		session.save(company);
		session.flush();
		session.close();
		
		return true;
	}

	@Override
	@Transactional
	public Company read(long company_id) {
		
		Session session = sf.getCurrentSession();
		
		Company company = session.get(Company.class, company_id); 
	
		return company;
	}

	@Override
	@Transactional
	public List<Company> readAll() {
		
		Session session = sf.getCurrentSession();
		
		return session.createQuery("from Company company").list();
		
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
