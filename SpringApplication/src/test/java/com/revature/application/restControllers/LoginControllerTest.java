package com.revature.application.restControllers;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.revature.application.RevatureSocialNetworkApplication;
import com.revature.application.dao.CompanyDao;
import com.revature.application.dao.EmployeeDao;
import com.revature.application.dao.beans.Company;
import com.revature.application.dao.beans.Employee;
import com.revature.application.dao.beans.Location;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RevatureSocialNetworkApplication.class)
public class LoginControllerTest {

	
	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	private MockMvc mockMvc;
	private MockHttpSession mockSession;
	private Employee employee;
	
	@Mock
	private EmployeeDao mockEmpDao;
		
	@InjectMocks
	private LoginController loginController;

	@Before
	public void setup() throws Exception {

		MockitoAnnotations.initMocks(this);
		mockSession = new MockHttpSession();

		this.mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();
			
		employee = new Employee();
		employee.setUsername("emp123");
		employee.setPassword("password");
		employee.setEmployeeId(12345L);

	}
	
	@Test
	public void emptyTest() {
	    
	}

	@Test
	public void loginPass() throws Exception {

		when(mockEmpDao.read(isA(String.class))).thenReturn(employee);

		RequestBuilder rb = post("/authentication/login")
				.accept(contentType)
				.param("username", employee.getUsername())
				.param("password", employee.getPassword());

		mockMvc.perform(rb).andExpect(status().isOk())
		.andExpect(content().contentType(contentType))
//		.andDo(print())
		.andExpect(jsonPath("$.message",is("Success")))
		.andExpect(jsonPath("$.success",is(true)));
	
	}
	
	@Test
	public void loginUsernameFail() throws Exception {

		when(mockEmpDao.read(isA(String.class))).thenReturn(null);

		RequestBuilder rb = post("/authentication/login")
				.accept(contentType)
				.param("username", employee.getUsername()+"0")
				.param("password", employee.getPassword());

		mockMvc.perform(rb).andExpect(status().isOk())
		.andExpect(content().contentType(contentType))
//		.andDo(print())
		.andExpect(jsonPath("$.message",is("User does not exist")))
		.andExpect(jsonPath("$.success",is(false)));
	
	}
	
	@Test
	public void loginPasswordFail() throws Exception {

		when(mockEmpDao.read(isA(String.class))).thenReturn(employee);

		RequestBuilder rb = post("/authentication/login")
				.accept(contentType)
				.param("username", employee.getUsername())
				.param("password", employee.getPassword()+"0");

		mockMvc.perform(rb).andExpect(status().isOk())
		.andExpect(content().contentType(contentType))
//		.andDo(print())
		.andExpect(jsonPath("$.message",is("Incorrect password")))
		.andExpect(jsonPath("$.success",is(false)));
	
	}

	@Test
	public void logout() throws Exception {

		RequestBuilder rb = get("/authentication/logout");

		mockMvc.perform(rb).andExpect(status().isOk())
		.andExpect(content().contentType(contentType))
//		.andDo(print())
		.andExpect(jsonPath("$.message",is("Success")))
		.andExpect(jsonPath("$.success",is(true)));
	
	}
	/*
	 * Do we have to mock a http session object?
	 * since we dont have a dao to mock?
	 * also what exactly is show with the RequestEmployee bean
	 */
	@Test
	public void userPass() throws Exception {
		
		mockSession.setAttribute("id",employee.getEmployeeId().toString());
		when(mockEmpDao.read(isA(Long.class))).thenReturn(employee);
		
		RequestBuilder rb = get("/authentication/user").session(mockSession);
		
//		RequestBuilder rb = get("/authentication/userNew");

		mockMvc.perform(rb).andExpect(status().isOk())
//		.andExpect(content().contentType(contentType))
		.andDo(print())
//		.andExpect(jsonPath("$.message",is("Success")))
//		.andExpect(jsonPath("$.employee.employeeId", is(employee.getEmployeeId())))
		.andExpect(jsonPath("$.success",is(true)));
	
	}
	
}
