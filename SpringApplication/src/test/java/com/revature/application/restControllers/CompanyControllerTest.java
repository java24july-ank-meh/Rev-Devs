package com.revature.application.restControllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.revature.application.RevatureSocialNetworkApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RevatureSocialNetworkApplication.class)
@WebAppConfiguration
public class CompanyControllerTest {

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Before
	public void setup() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	/*
	 * Only testing for success and correct response type, responses must also be
	 * tested for correct data response
	 */

	@Test
	public void returnAllCompanies() throws Exception {
		mockMvc.perform(get("/companies")).andExpect(status().isOk()).andExpect(content().contentType(contentType));
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
