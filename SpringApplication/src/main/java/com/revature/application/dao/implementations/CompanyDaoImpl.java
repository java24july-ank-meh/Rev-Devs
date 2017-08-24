package com.revature.application.dao.implementations;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revature.application.dao.CompanyDao;
import com.revature.application.dao.beans.Company;

@Service
public class CompanyDaoImpl implements CompanyDao {

	@Autowired
	SessionFactory sf;
	
	public void setSf(SessionFactory sf) {
		this.sf = sf;
	}
	
	@Override
	@Transactional
	public boolean create(Company company) {
	
		Session session = sf.getCurrentSession();
		session.save(company);
		session.flush();
		
		return true;
	}

	@Override
	@Transactional
	public Company read(long company_id) {
		
		Session session = sf.getCurrentSession();
		
		Company company = session.get(Company.class, company_id);
		session.flush();
	
		return company;
	}

	@Override
	@Transactional
	public List<Company> readAll() {
		
		Session session = sf.getCurrentSession();
		
		List<Company> companies = session.createQuery("from Company company").list();
		session.flush();
		
		return companies;
		
	}

	@Override
	@Transactional
	public boolean update(Company company) {
		
		Session session = sf.getCurrentSession();
		session.update(company);
		session.flush();
		
		return true;
		
	}

	@Override
	@Transactional
	public boolean delete(Company company) {
		
		Session session = sf.getCurrentSession();
		session.delete(company);
		session.flush();
		
		return true;
	}

	@Override
	@Transactional
	public boolean deleteById(long company_id) {
		
		Session session = sf.getCurrentSession();
		
		Company company = new Company();
		company.setCompanyId(company_id);
		session.delete(company);
		session.flush();
		
		return false;
	}



}
