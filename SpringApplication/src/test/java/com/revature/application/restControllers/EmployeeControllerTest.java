package com.revature.application.restControllers;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.revature.application.RevatureSocialNetworkApplication;
import com.revature.application.dao.EmployeeDao;
import com.revature.application.dao.beans.Company;
import com.revature.application.dao.beans.Employee;
import com.revature.application.dao.beans.Location;
import com.revature.application.dao.beans.forms.EmployeeForm;
import com.revature.application.services.LoginOperations;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RevatureSocialNetworkApplication.class)
public class EmployeeControllerTest {
    
    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
    
    private MockMvc mockMvc;
    
    @Mock
    private LoginOperations mockLOperation;
    @Mock
    private EmployeeDao employeeDAO;
    @InjectMocks
    private EmployeeController employeeController;
    
    // Global objects we want to inject
    Location location;
    Company company;
    
    Employee employee1;
    Employee employee2;
    List<Employee> employees;
    
    @Before
    public void setup() throws Exception {
        
        MockitoAnnotations.initMocks(this);
        
        this.mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
        
        // Set up the employee information
        location = new Location("Reston", 123.0, 123.0);
        company = new Company(location, "company");
        
        employee1 = new Employee(location, company, "username1", "password1", "email1", "fname1",
                "lname1");
        employee1.setEmployeeId(1L);
        
        employee2 = new Employee(location, company, "username2", "password2", "email2", "fname2",
                "lname2");
        employee2.setEmployeeId(2L);
        
        employees = new ArrayList<>();
        
        employees.add(employee1);
        employees.add(employee2);
    }
    
    /*
     * logged in validation pass
     */
    @Test
    public void returnAllUsersPass() throws Exception {
    	when(mockLOperation.isLoggedIn()).thenReturn(true);
        when(employeeDAO.readAll()).thenReturn(employees);
        
        mockMvc.perform(get("/employees")).andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].employeeId", Matchers.is(1)))
                .andExpect(jsonPath("$[0].location", Matchers.notNullValue()))
                .andExpect(jsonPath("$[0].company", Matchers.notNullValue()))
                .andExpect(jsonPath("$[0].username", Matchers.is(employees.get(0).getUsername())))
                .andExpect(jsonPath("$[0].email", Matchers.is(employees.get(0).getEmail())))
                .andExpect(jsonPath("$[0].fname", Matchers.is(employees.get(0).getFname())))
                .andExpect(jsonPath("$[0].lname", Matchers.is(employees.get(0).getLname())))
                .andExpect(jsonPath("$[1].employeeId", Matchers.is(2)))
                .andExpect(jsonPath("$[1].location", Matchers.notNullValue()))
                .andExpect(jsonPath("$[1].company", Matchers.notNullValue()))
                .andExpect(jsonPath("$[1].username", Matchers.is(employees.get(1).getUsername())))
                .andExpect(jsonPath("$[1].email", Matchers.is(employees.get(1).getEmail())))
                .andExpect(jsonPath("$[1].fname", Matchers.is(employees.get(1).getFname())))
                .andExpect(jsonPath("$[1].lname", Matchers.is(employees.get(1).getLname())));
    }
    
    /*
     * logged in validation pass
     */
    @Test
    public void returnSingleUser() throws Exception {
    	when(mockLOperation.isLoggedIn()).thenReturn(true);
        when(employeeDAO.read(employee1.getEmployeeId())).thenReturn(employee1);
        
        mockMvc.perform(get("/employees/1")).andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.employeeId", Matchers.is(1)))
                .andExpect(jsonPath("$.location", Matchers.notNullValue()))
                .andExpect(jsonPath("$.company", Matchers.notNullValue()))
                .andExpect(jsonPath("$.username", Matchers.is(employee1.getUsername())))
                .andExpect(jsonPath("$.email", Matchers.is(employee1.getEmail())))
                .andExpect(jsonPath("$.fname", Matchers.is(employee1.getFname())))
                .andExpect(jsonPath("$.lname", Matchers.is(employee1.getLname())));
    }
    
    @Test
    public void createUser() throws Exception {
        
        when(employeeDAO.create(any(EmployeeForm.class))).thenReturn(true);
        
        RequestBuilder builder = post("/employees")
                .param("locationId", "1")
                .param("companyId", "1")
                .param("username", employee1.getUsername())
                .param("password", employee1.getPassword())
                .param("email", employee1.getEmail())
                .param("fname", employee1.getFname())
                .param("lname", employee1.getLname());
          
        mockMvc.perform(builder).andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.success", Matchers.is(true)))
                .andExpect(jsonPath("$.message", Matchers.is("Success")));
    }
    
    @Test
    public void createUserMissingParam() throws Exception {
        
        when(employeeDAO.create(any(EmployeeForm.class))).thenReturn(true);
        
        RequestBuilder builder = post("/employees")
                .param("locationId", "1")
                .param("companyId", "1")
                .param("username", employee1.getUsername())
                .param("fname", employee1.getFname())
                .param("lname", employee1.getLname());
          
        mockMvc.perform(builder).andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.success", Matchers.is(false)));
    }
    
    @Test
    public void createUserMissingParamMustSucceed() throws Exception {
        
        when(employeeDAO.create(any(EmployeeForm.class))).thenReturn(true);
        
        RequestBuilder builder = post("/employees")
                .param("username", employee1.getUsername())
                .param("email", employee1.getEmail())
                .param("password", employee1.getPassword())
                .param("fname", employee1.getFname())
                .param("lname", employee1.getLname());
          
        mockMvc.perform(builder).andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.success", Matchers.is(true)))
                .andExpect(jsonPath("$.message", Matchers.is("Success")));
    }
    
    /*
     * logged in validation pass
     */
    @Test
    public void updateUserPass() throws Exception {
    	when(mockLOperation.isLoggedIn()).thenReturn(true);
        when(employeeDAO.update(anyLong(), any(EmployeeForm.class)))
                .thenReturn(true);
        
        RequestBuilder builder = put("/employees/1")
                .param("locationId", "1")
                .param("companyId", "1")
                .param("username", employee1.getUsername())
                .param("email", employee1.getEmail())
                .param("fname", employee1.getFname())
                .param("lname", employee1.getLname());
        
        
        mockMvc.perform(builder).andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.success", Matchers.is(true)))
                .andExpect(jsonPath("$.message", Matchers.is("Success")));
    }
    
    /*
     * logged in validation pass
     */
    @Test
    public void updateUserMissingParam() throws Exception {
    	when(mockLOperation.isLoggedIn()).thenReturn(true);
        when(employeeDAO.update(anyLong(), any(EmployeeForm.class)))
                .thenReturn(true);
        
        RequestBuilder builder = put("/employees/1")
                .param("locationId", "1")
                .param("companyId", "1")
                .param("email", employee1.getEmail())
                .param("fname", employee1.getFname())
                .param("lname", employee1.getLname());
        
        
        mockMvc.perform(builder).andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.success", Matchers.is(false)));
    }
    
    /*
     * logged in validation pass
     */
    @Test
    public void deleteUser() throws Exception {
    	when(mockLOperation.isLoggedIn()).thenReturn(true);
        when(employeeDAO.delete(any(Employee.class))).thenReturn(true);
        
        mockMvc.perform(delete("/employees/1")).andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.success", Matchers.is(true)))
                .andExpect(jsonPath("$.message", Matchers.is("Success")));
    }
    
}
