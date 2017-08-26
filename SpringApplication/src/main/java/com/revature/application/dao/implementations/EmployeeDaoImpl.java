package com.revature.application.dao.implementations;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revature.application.dao.EmployeeDao;
import com.revature.application.dao.beans.Company;
import com.revature.application.dao.beans.Employee;
import com.revature.application.dao.beans.Location;
import com.revature.application.dao.beans.forms.EmployeeForm;

@Service
public class EmployeeDaoImpl implements EmployeeDao {
    
    @Autowired
    SessionFactory sf;
    
    @Override
    @Transactional
    public boolean create(EmployeeForm employeeForm) {
        
        Session session = sf.getCurrentSession();
        
        Location location = new Location();
        location.setLocationId(employeeForm.getLocationId());
        
        Company company = new Company();
        company.setCompanyId(employeeForm.getCompanyId());
        
        Employee employee = new Employee(location, company, employeeForm.getUsername(),
                employeeForm.getPassword(), employeeForm.getEmail(), employeeForm.getFname(),
                employeeForm.getLname());
        
        session.save(employee);
        session.flush();
        
        return true;
    }
    
    @Override
    @Transactional
    public Employee read(long employee_id) {
        
        Session session = sf.getCurrentSession();
        
        Employee employee = session.get(Employee.class, employee_id);
        session.flush();
        
        return employee;
    }
    
    @Override
    @Transactional
    public Employee read(String username) {
        
        Session session = sf.getCurrentSession();
        
        String query = "from Employee employee where employee.username = :username";
        Employee employee = (Employee) session.createQuery(query).setParameter("username", username)
                .uniqueResult();
        
        session.flush();
        
        return employee;
    }
    
    @Override
    @Transactional
    public List<Employee> readAll() {
        
        Session session = sf.getCurrentSession();
        
        String query = "from Employee employee";
        List<Employee> employees = session.createQuery(query).list();
        session.flush();
        
        return employees;
        
    }
    
    @Override
    @Transactional
    public boolean update(long employee_id, EmployeeForm employeeForm) {
        
        Session session = sf.getCurrentSession();
        
        Location location = new Location();
        location.setLocationId(employeeForm.getLocationId());
        
        Company company = new Company();
        company.setCompanyId(employeeForm.getCompanyId());
        
        Employee employee = new Employee(location, company, employeeForm.getUsername(),
                employeeForm.getPassword(), employeeForm.getEmail(), employeeForm.getFname(),
                employeeForm.getLname());
        employee.setEmployeeId(employee_id);
        
        session.update(employee);
        session.flush();
        
        return true;
        
    }
    
    @Override
    @Transactional
    public boolean delete(Employee emp) {
        
        Session session = sf.getCurrentSession();
        
        session.delete(emp);
        session.flush();
        
        return true;
    }
    
    @Override
    @Transactional
    public boolean deleteById(long employee_id) {
        
        Session session = sf.getCurrentSession();
        
        Employee employee = new Employee();
        employee.setEmployeeId(employee_id);
        
        session.delete(employee);
        session.flush();
        
        return true;
        
    }
    
}
