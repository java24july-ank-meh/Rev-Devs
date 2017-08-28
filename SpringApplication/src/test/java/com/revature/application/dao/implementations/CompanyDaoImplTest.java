package com.revature.application.dao.implementations;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.revature.application.RevatureSocialNetworkApplication;
import com.revature.application.dao.CompanyDao;
import com.revature.application.dao.beans.Company;
import com.revature.application.dao.beans.Location;
import com.revature.application.dao.beans.forms.CompanyForm;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RevatureSocialNetworkApplication.class)
@Transactional
public class CompanyDaoImplTest {
    
    @Autowired
    private SessionFactory sf;
    
    @Autowired
    private CompanyDao companyDAO;
        
    // Objects we want to attach to companies
    Location location = null;
    
    // Objects we want to persist in db
    Company company1 = null;
    Company company2 = null;
    Company company3 = null;
    
    @Before
    public void setup() {
        
        Session session = sf.getCurrentSession();
        
        location = new Location("LA", 123.12, 123.12);
        
        session.saveOrUpdate(location);
                
        company1 = new Company(location, "Fintech");
        company2 = new Company(location, "Techtech");
        company3 = new Company(location, "TicTech");
        
        session.saveOrUpdate(company1);
        session.saveOrUpdate(company2);
        // The last company3 will be used to persist using our api
        
    }
    
    @Test
    public void locationMustBeInDB() {
                
        Session session = sf.getCurrentSession();
        
        Location newLocation = (Location) session.get(Location.class, location.getLocationId());
        
        assertTrue("Location object must be persisted to DB", newLocation != null);
    }
    
    @Test
    public void createMethodMustSaveToDb() {
        
        Session session = sf.getCurrentSession();
        
        CompanyForm companyForm = new CompanyForm(company3.getLocation().getLocationId(),
                company3.getCompanyName());
        
        companyDAO.create(companyForm);
        
        String query = "from Company company where company.companyName = :name";
        Company newCompany = (Company) session.createQuery(query)
                .setParameter("name", company3.getCompanyName())
                .uniqueResult();
        
        assertTrue("Company object must be persisted to DB", newCompany != null);
        assertTrue(newCompany.getCompanyName().equals(company3.getCompanyName()));        
    }
    
    @Test
    public void readGetsTheProperCompanyObject() {
            
        Session session = sf.getCurrentSession();
        
        Company newCompany = companyDAO.read(company1.getCompanyId());
        
        assertTrue("Company object must not be null", newCompany != null);
        assertTrue(newCompany.getCompanyId().equals(company1.getCompanyId()));
        assertTrue(newCompany.getCompanyName().equals(company1.getCompanyName()));        
        
    }
    
    @Test
    public void readAllGetsAllCompanyObjects() {
                
        Session session = sf.getCurrentSession();
        
        List<Company> companies = companyDAO.readAll();
        
        assertTrue("Companies must not be null", companies != null);
        
        companies.sort((item1, item2) -> {
            if (item1.getCompanyId() < item2.getCompanyId()) {
                return -1;
            } else if (item1.getCompanyId() > item2.getCompanyId()) {
                return 1;
            } else {
                return 0;
            }
        });
   
        assertTrue(companies.size() == 2);
        
        assertTrue(companies.get(0).getCompanyId().equals(company1.getCompanyId()));
        assertTrue(companies.get(1).getCompanyId().equals(company2.getCompanyId()));
        
        assertTrue(companies.get(0).getCompanyName().equals(company1.getCompanyName()));
        assertTrue(companies.get(1).getCompanyName().equals(company2.getCompanyName()));
                
    }
    
    @Test
    public void updateMustChangeCompany() {
        
        Session session = sf.getCurrentSession();
        
        CompanyForm form = new CompanyForm(location.getLocationId(), "Changed");
        
        companyDAO.update(company1.getCompanyId(), form);
        
        Company newCompany = (Company) session.get(Company.class, company1.getCompanyId());
        
        assertTrue("New Company must not be null", newCompany != null);
        assertTrue(newCompany.getCompanyId().equals(company1.getCompanyId()));
        assertTrue(newCompany.getCompanyName().equals("Changed"));
        
    }
    
    
    @Test
    public void deleteMustDeleteCompany() {
        
        Session session = sf.getCurrentSession();
        
        String query = "from Company company where company.companyName = :name";
        Company newCompany = (Company) session.createQuery(query)
                .setParameter("name", company1.getCompanyName())
                .uniqueResult();
        
        companyDAO.delete(newCompany);
        
        Company testCompany = session.get(Company.class, newCompany.getCompanyId());
        
        assertTrue("Test company must be null", testCompany == null);
    }
    
    
    @Test
    public void deleteByIdMustDeleteCompany() {
        
        Session session = sf.getCurrentSession();
        
        String query = "from Company company where company.companyName = :name";
        Company newCompany = (Company) session.createQuery(query)
                .setParameter("name", company1.getCompanyName())
                .uniqueResult();
                
        long companyId = newCompany.getCompanyId();
        companyDAO.deleteById(companyId);
                        
        Company testCompany = (Company) session.get(Company.class, companyId);
        
        assertTrue("Test company must be null", testCompany == null);
    }
}
