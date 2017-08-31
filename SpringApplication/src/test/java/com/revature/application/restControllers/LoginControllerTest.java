package com.revature.application.restControllers;

import static org.hamcrest.CoreMatchers.is;
//import static org.mockito.Matchers.any;
import static org.mockito.Matchers.isA;
import static org.mockito.Matchers.isNull;
import static org.mockito.Matchers.matches;
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
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.revature.application.RevatureSocialNetworkApplication;
import com.revature.application.dao.CompanyDao;
import com.revature.application.dao.EmployeeDao;
import com.revature.application.dao.LocationDao;
import com.revature.application.dao.beans.Company;
import com.revature.application.dao.beans.Employee;
import com.revature.application.dao.beans.Location;
import com.revature.application.services.LoginOperations;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RevatureSocialNetworkApplication.class)
public class LoginControllerTest {

	
	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	private MockMvc mockMvc;
	private MockHttpSession mockSession;
	private Employee employee;
	private Location location;
	
	@Mock
	private EmployeeDao mockEmpDao;
	@Mock
	private LocationDao mockLocDao;
	@Mock
	private LoginOperations mockLOperation;
		
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
		employee.setFname("John");
		employee.setLname("Doe");
		employee.setEmail("jd@mail.com");

		location = new Location();
		location.setCity("new york");
		location.setLocationId(12345L);
		
	}
	
	@Test
	public void emptyTest() {
	    
	}

	@Test
	public void loginPass() throws Exception {

		when(mockLOperation.isLoggedIn()).thenReturn(true);
		
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
	public void loginFail() throws Exception {

		when(mockLOperation.isLoggedIn()).thenReturn(false);
		
		RequestBuilder rb = post("/authentication/login")
				.accept(contentType)
				.param("username", employee.getUsername()+0)
				.param("password", employee.getPassword()+0);

		mockMvc.perform(rb).andExpect(status().isOk())
		.andExpect(content().contentType(contentType))
//		.andDo(print())
		.andExpect(jsonPath("$.message",is("Login was not successful")))
		.andExpect(jsonPath("$.success",is(false)));
	
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
		.andExpect(jsonPath("$.message",is("Login was not successful")))
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
		.andExpect(jsonPath("$.message",is("Login was not successful")))
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
		
		mockMvc.perform(rb).andExpect(status().isOk())
		.andDo(print())
		.andExpect(jsonPath("$.employee.username", is(employee.getUsername())))
		.andExpect(jsonPath("$.success",is(true)));
	}
	
	@Test
	public void userFailValidation() throws Exception {

		RequestBuilder rb = get("/authentication/user").session(mockSession);
		
		mockMvc.perform(rb).andExpect(status().isOk())
//		.andDo(print())
		.andExpect(jsonPath("$.success",is(false)));
	
	}
	
	@Test
	public void upadateProfileSuccess() throws Exception {
		mockSession.setAttribute("id",employee.getEmployeeId().toString());
		when(mockEmpDao.read(isA(Long.class))).thenReturn(employee);
		
		RequestBuilder rb = post("/authentication/update-profile")
				.accept(contentType)
				.session(mockSession)
				.param("username", employee.getUsername())
				.param("email", employee.getEmail())
				.param("fname", employee.getFname())
				.param("lname", employee.getLname());
		
		mockMvc.perform(rb).andExpect(status().isOk())
//		.andDo(print())
		.andExpect(jsonPath("$.message",is("Success")))
		.andExpect(jsonPath("$.success",is(true)));
	
	}
		
	//false because no employee read, meaning no session or no valid id
	@Test
	public void upadateProfileFail() throws Exception {
		
		when(mockEmpDao.read(isA(Long.class))).thenReturn(null);
		
		RequestBuilder rb = post("/authentication/update-profile");
		
		mockMvc.perform(rb).andExpect(status().isOk())
//		.andDo(print())
		.andExpect(jsonPath("$.message",is("Not logged in")))
		.andExpect(jsonPath("$.success",is(false)));
	
	}
	
	@Test
	public void setLocationSuccess() throws Exception {
		
		mockSession.setAttribute("id",employee.getEmployeeId().toString());
		when(mockEmpDao.read(isA(Long.class))).thenReturn(employee);
		when(mockLocDao.read(isA(String.class))).thenReturn(location);
		
		RequestBuilder rb = post("/authentication/set-location")
				.session(mockSession)
				.param("city", location.getCity());
		
		mockMvc.perform(rb).andExpect(status().isOk())
//		.andDo(print())
		.andExpect(jsonPath("$.message",is("Success")))
		.andExpect(jsonPath("$.success",is(true)));
	
	}
	
	@Test
	public void setLocationFailValidation() throws Exception {
				
		RequestBuilder rb = post("/authentication/set-location").session(mockSession);;
		
		mockMvc.perform(rb).andExpect(status().isOk())
//		.andDo(print())
		.andExpect(jsonPath("$.message",is("Not logged in")))
		.andExpect(jsonPath("$.success",is(false)));
	
	}
	
	@Test
	public void setLocationFailNoLocation() throws Exception {
		
		mockSession.setAttribute("id",employee.getEmployeeId().toString());
		when(mockEmpDao.read(isA(Long.class))).thenReturn(employee);
		when(mockLocDao.read(isA(Long.class))).thenReturn(null);

		RequestBuilder rb = post("/authentication/set-location")
				.session(mockSession)
				.param("city", "new york");
		
		mockMvc.perform(rb).andExpect(status().isOk())
//		.andDo(print())
		.andExpect(jsonPath("$.message",is("location not found")))
		.andExpect(jsonPath("$.success",is(false)));
	
	}
	
}
