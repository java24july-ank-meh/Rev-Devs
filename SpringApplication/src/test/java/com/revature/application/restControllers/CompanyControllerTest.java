package com.revature.application.restControllers;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.isA;
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
import org.mockito.Matchers;
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
import com.revature.application.dao.beans.forms.CompanyForm;

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
		companies = new ArrayList<>();

		Company company = new Company(new Location(), "Bobs");
		company.setCompanyId(1L);

		companies.add(company);

		company = new Company();
		company.setCompanyId(8L);
		company.setCompanyName("Brix Phones");

		companies.add(company);

//		System.out.println("Setting up Company Controller Test List");
//		for(Company c: companies) {
//			System.out.println(c);
//		}
	}

	/*
	 * Only testing for success and correct response type, responses must also be
	 * tested for correct data response
	 */

//	@Test
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
		//		.andDo(print())
		.andExpect(jsonPath("$[0].companyId", is(cId)))
		.andExpect(jsonPath("$[0].companyName", is(companies.get(0).getCompanyName())));

	}

//	@Test
	public void returnSingleCompany() throws Exception {

		when(mockComDao.read(any(Long.class))).thenReturn(companies.get(0));

		int cId = (int) companies.get(0).getCompanyId();
		RequestBuilder rb = get("/companies/" + cId).accept(contentType);

		mockMvc.perform(rb).andExpect(status().isOk())
		.andExpect(content().contentType(contentType))
//		.andDo(print())
		.andExpect(status().is2xxSuccessful())
		.andExpect(jsonPath("$.companyId", is(cId)))
		.andExpect(jsonPath("$.companyName", is(companies.get(0).getCompanyName())));

	}

	@Test
	public void createCompanyPass() throws Exception {

	    when(mockComDao.create(any(CompanyForm.class))).thenReturn(true);

		RequestBuilder rb = post("/companies").param("companyName", companies.get(1).getCompanyName());
		//.accept(contentType);

		mockMvc.perform(rb).andExpect(status().isOk())
		.andExpect(content().contentType(contentType))
//		.andDo(print())
		.andExpect(jsonPath("$.message",is("Success")))
		.andExpect(jsonPath("$.success",is(true)));
	}

	@Test
	public void createCompanyFail() throws Exception {
		RequestBuilder rb = post("/companies");
		
		mockMvc.perform(rb).andExpect(status().isOk())
		.andExpect(content().contentType(contentType))
		.andDo(print())
		.andExpect(jsonPath("$.message",is("Failed to create new company")))
		.andExpect(jsonPath("$.success",is(false)));
	}

//	@Test
	public void deleteCompany() throws Exception {

		when(mockComDao.deleteById(isA(Long.class))).thenReturn(true);

		RequestBuilder rb = delete("/companies/"+companies.get(1).getCompanyId()).param("companyName", companies.get(1).getCompanyName());

		mockMvc.perform(rb)
		.andExpect(status().isOk())
		.andExpect(content().contentType(contentType))
//		.andDo(print())
		.andExpect(jsonPath("$.message",is("Success")))
		.andExpect(jsonPath("$.success",is(true)));;
	}	

}
