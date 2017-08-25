package com.revature.application.restControllers;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.revature.application.RevatureSocialNetworkApplication;
import com.revature.application.dao.beans.Company;
import com.revature.application.dao.beans.Location;
import com.revature.application.dao.implementations.CompanyDaoImpl;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RevatureSocialNetworkApplication.class)
@WebAppConfiguration
public class CompanyControllerTest {

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	private MockMvc mockMvc;
	
	// Some global list of comapnies
	private List<Company> companies;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Before
	public void setup() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		
		
		// Setup all the companies info
		Company company = new Company(new Location(), "Bobs");
		company.setCompanyId(1);
		
		companies = new ArrayList<>();
		companies.add(company);
	}

	/*
	 * Only testing for success and correct response type, responses must also be
	 * tested for correct data response
	 */

	@Test
	public void returnAllCompanies() throws Exception {
		
		// Mock the method that the api will call
		CompanyDaoImpl listMock = mock(CompanyDaoImpl.class);
		// When the method calls .readAll(), mockito will not call it
		// and return the companies list instead
		when(listMock.readAll()).thenReturn(companies);
		
		
		// Now call the api
		RequestBuilder rb = get("/companies").accept(contentType);
		
		// Test fails right now
		mockMvc.perform(rb)
			.andExpect(status().isOk())
			.andExpect(content().contentType(contentType))
			.andExpect(jsonPath("$[0]", is(companies.get(0))))
			.andExpect(jsonPath("$[0].companyId", is(1)));
	}

	@Test
	public void returnSingleCompany() throws Exception {
		// Must be changed to mock a location object
		mockMvc.perform(get("/companies/1")).andExpect(status().isOk()).andExpect(content().contentType(contentType));
	}

	@Test
	public void createCompany() throws Exception {
		mockMvc.perform(post("/companies")).andExpect(status().isOk()).andExpect(content().contentType(contentType));
	}

	@Test
	public void deleteCompany() throws Exception {
		mockMvc.perform(delete("/companies/1")).andExpect(status().isOk())
				.andExpect(content().contentType(contentType));
	}

}
