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
import com.revature.application.dao.EmployeeDao;
import com.revature.application.dao.beans.Company;
import com.revature.application.dao.beans.Employee;
import com.revature.application.dao.beans.Location;
import com.revature.application.dao.beans.forms.EmployeeForm;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RevatureSocialNetworkApplication.class)
@Transactional
public class EmployeeDaoImplTest {
    
    @Autowired
    private SessionFactory sf;
    
    @Autowired
    private EmployeeDao employeeDAO;
    
    // Objects we want to attach to companies
    Location location = null;
    Company company = null;
    
    // Objects we want to persist in db
    Employee employee1 = null;
    Employee employee2 = null;
    Employee employee3 = null;
    
    @Before
    public void setup() {
        
        Session session = sf.getCurrentSession();
        
        location = new Location("LA", 123.12, 123.12);
        company = new Company(location, "tech");
        
        session.saveOrUpdate(location);
        session.saveOrUpdate(company);
        
        employee1 = new Employee(location, company, "username1", "password1", "email1", "fname1",
                "lnam1");
        employee2 = new Employee(location, company, "username2", "password2", "email2", "fname2",
                "lnam2");
        employee3 = new Employee(location, company, "username3", "password3", "email3", "fname3",
                "lnam3");
        
        session.saveOrUpdate(employee1);
        session.saveOrUpdate(employee2);
        // The last company3 will be used to persist using our api
        
    }
    
    @Test
    public void locationAndCompanyMustBeInDB() {
        
        Session session = sf.getCurrentSession();
        
        Location newLocation = (Location) session.get(Location.class, location.getLocationId());
        Company newCompany = (Company) session.get(Company.class, company.getCompanyId());
        
        assertTrue("Location object must be persisted to DB", newLocation != null);
        assertTrue("Company object must be persisted to DB", newCompany != null);
    }
    
    @Test
    public void createMethodMustSaveToDb() {
        
        Session session = sf.getCurrentSession();
        
        EmployeeForm employeeForm = new EmployeeForm(location.getLocationId(),
                company.getCompanyId(), employee3.getUsername(), employee3.getPassword(),
                employee3.getEmail(), employee3.getFname(), employee3.getLname());
        
        employeeDAO.create(employeeForm);
        
        String query = "from Employee employee where employee.username = :username";
        Employee newEmployee = (Employee) session.createQuery(query)
                .setParameter("username", employee3.getUsername()).uniqueResult();
        
        assertTrue("Company object must be persisted to DB", newEmployee != null);
        assertTrue(newEmployee.getUsername().equals(employee3.getUsername()));
        assertTrue(newEmployee.getPassword().equals(employee3.getPassword()));
        assertTrue(newEmployee.getEmail().equals(employee3.getEmail()));
        assertTrue(newEmployee.getLname().equals(employee3.getLname()));
        assertTrue(newEmployee.getFname().equals(employee3.getFname()));
        
    }
    
    @Test
    public void readGetsTheProperEmployeeObject() {
        
        Session session = sf.getCurrentSession();
        
        Employee newEmployee = employeeDAO.read(employee1.getEmployeeId());
        
        assertTrue("Employee object must not be null", newEmployee != null);
        assertTrue(newEmployee.getEmployeeId() == employee1.getEmployeeId());
        assertTrue(newEmployee.getUsername().equals(employee1.getUsername()));
        assertTrue(newEmployee.getPassword().equals(employee1.getPassword()));
        assertTrue(newEmployee.getEmail().equals(employee1.getEmail()));
        assertTrue(newEmployee.getLname().equals(employee1.getLname()));
        assertTrue(newEmployee.getFname().equals(employee1.getFname()));
        
    }
    
    @Test
    public void readAllGetsAllEmployeeObjects() {
        
        Session session = sf.getCurrentSession();
        
        List<Employee> employees = employeeDAO.readAll();
        
        assertTrue("Companies must not be null", employees != null);
        
        employees.sort((item1, item2) -> {
            if (item1.getEmployeeId() < item2.getEmployeeId()) {
                return -1;
            } else if (item1.getEmployeeId() > item2.getEmployeeId()) {
                return 1;
            } else {
                return 0;
            }
        });
        
        assertTrue(employees.size() == 2);
        
        assertTrue(employees.get(0).getEmployeeId() == employee1.getEmployeeId());
        assertTrue(employees.get(1).getEmployeeId() == employee2.getEmployeeId());
        
        assertTrue(employees.get(0).getUsername() == employee1.getUsername());
        assertTrue(employees.get(1).getUsername() == employee2.getUsername());
        
        assertTrue(employees.get(0).getEmail() == employee1.getEmail());
        assertTrue(employees.get(1).getEmail() == employee2.getEmail());
        
        assertTrue(employees.get(0).getFname() == employee1.getFname());
        assertTrue(employees.get(1).getFname() == employee2.getFname());
        
        assertTrue(employees.get(0).getLname() == employee1.getLname());
        assertTrue(employees.get(1).getLname() == employee2.getLname());
        
        assertTrue(employees.get(0).getPassword() == employee1.getPassword());
        assertTrue(employees.get(1).getPassword() == employee2.getPassword());
    }
    
    @Test
    public void updateMustChangeEmployee() {
        
        Session session = sf.getCurrentSession();
        
        EmployeeForm form = new EmployeeForm(location.getLocationId(), company.getCompanyId(),
                "Changed", "Changed", "Changed",
                "Changed", "Changed");
        
        employeeDAO.update(employee1.getEmployeeId(), form);
        
        Employee newEmployee = (Employee) session.get(Employee.class, employee1.getEmployeeId());
        
        assertTrue("New Company must not be null", newEmployee != null);
        
        assertTrue(newEmployee.getEmployeeId() == employee1.getEmployeeId());
        assertTrue(newEmployee.getUsername().equals("Changed"));
        assertTrue(newEmployee.getPassword().equals("Changed"));
        assertTrue(newEmployee.getEmail().equals("Changed"));
        assertTrue(newEmployee.getFname().equals("Changed"));
        assertTrue(newEmployee.getLname().equals("Changed"));
        
    }
    
    @Test
    public void deleteMustDeleteEmployee() {
        
        Session session = sf.getCurrentSession();
        
        String query = "from Employee employee where employee.username = :username";
        Employee newEmployee = (Employee) session.createQuery(query)
                .setParameter("username", employee1.getUsername()).uniqueResult();
        
        employeeDAO.delete(newEmployee);
        
        Employee testEmployee = session.get(Employee.class, newEmployee.getEmployeeId());
        
        assertTrue("Test company must be null", testEmployee == null);
    }
    
    @Test
    public void deleteByIdMustDeleteEmployee() {
        
        Session session = sf.getCurrentSession();
        
        String query = "from Employee employee where employee.username = :username";
        Employee newEmployee = (Employee) session.createQuery(query)
                .setParameter("username", employee1.getUsername()).uniqueResult();
        
        long employeeId = newEmployee.getEmployeeId();
        employeeDAO.deleteById(employeeId);
        
        Employee testEmployee = (Employee) session.get(Employee.class, employeeId);
        
        assertTrue("Test company must be null", testEmployee == null);
    }
}
