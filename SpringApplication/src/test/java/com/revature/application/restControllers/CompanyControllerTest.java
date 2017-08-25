package com.revature.application.restControllers;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.util.*;

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
import com.revature.application.dao.CompanyDao;
import com.revature.application.dao.beans.Company;
import com.revature.application.dao.beans.Location;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RevatureSocialNetworkApplication.class)
public class CompanyControllerTest {
	
	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	private MockMvc mockMvc;

	// Some global list of comapnies
	private List<Company> companies;

	@Mock
	private CompanyDao mockComDao;
	@InjectMocks
	private CompanyController companyController;

	@Before
	public void setup() throws Exception {

		MockitoAnnotations.initMocks(this);

		this.mockMvc = MockMvcBuilders.standaloneSetup(companyController).build();

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

		// When the method calls .readAll(), mockito will not call it
		// and return the companies list instead
		when(mockComDao.readAll()).thenReturn(companies);

		// Now call the api
		RequestBuilder rb = get("/companies").accept(contentType);

		int cId = (int) companies.get(0).getCompanyId();

		// Test passes
		mockMvc.perform(rb).andExpect(status().isOk())
		.andExpect(content().contentType(contentType))
		.andDo(print())
		.andExpect(jsonPath("$[0].companyId", is(cId)))
		.andExpect(jsonPath("$[0].companyName", is(companies.get(0).getCompanyName())));

	}

	@Test
	public void returnSingleCompany() throws Exception {

		when(mockComDao.readAll()).thenReturn(companies);

		int cId = (int) companies.get(0).getCompanyId();
		RequestBuilder rb = get("/companies/" + cId).accept(contentType);

		mockMvc.perform(rb).andExpect(status().isOk())
		.andExpect(content().contentType(contentType))
//		.andDo(print())
		.andExpect(jsonPath("$.companyId", is(cId)))
		.andExpect(jsonPath("$.companyName", is(companies.get(0).getCompanyName())));

	}

//	@Test
	public void createCompany() throws Exception {
		mockMvc.perform(post("/companies")).andExpect(status().isOk()).andExpect(content().contentType(contentType));
	}

//	@Test
	public void deleteCompany() throws Exception {
		mockMvc.perform(delete("/companies/1")).andExpect(status().isOk())
		.andExpect(content().contentType(contentType));
	}	

}
