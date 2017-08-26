package com.revature.application.dao.implementations;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revature.application.dao.CompanyDao;
import com.revature.application.dao.beans.Company;
import com.revature.application.dao.beans.Location;
import com.revature.application.dao.beans.forms.CompanyForm;

@Service
public class CompanyDaoImpl implements CompanyDao {

	@Autowired
	SessionFactory sf;
	
	@Override
	@Transactional
	public boolean create(CompanyForm companyForm) {
	
		Session session = sf.getCurrentSession();
		
		Location location = new Location();
		location.setLocationId(companyForm.getLocationId());
			
		Company company = new Company(location, companyForm.getCompanyName());
		
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
	public boolean update(long company_id, CompanyForm companyForm) {
		
		Session session = sf.getCurrentSession();
		
		Location location = (Location) session.get(Location.class, companyForm.getLocationId());
		
		Company company = (Company) session.get(Company.class, company_id);
		
		company.setLocation(location);
		company.setCompanyName(companyForm.getCompanyName());
	
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
		
		// Not deleting with a query
		// String hql = "delete Company where companyId = :companyId";
		// session.createQuery(hql).setLong("companyId", company_id).executeUpdate();
		
		Company company = (Company) session.get(Company.class, company_id);
		session.delete(company);
		session.flush();
				
		return true;
	}



}
