package com.revature.application.restControllers;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.util.*;

import javax.servlet.http.HttpSession;

import org.hamcrest.Matchers;
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
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.revature.application.RevatureSocialNetworkApplication;
import com.revature.application.dao.CompanyDao;
import com.revature.application.dao.beans.Company;
import com.revature.application.dao.beans.Location;
import com.revature.application.dao.beans.forms.CompanyForm;
import com.revature.application.services.LoginOperations;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RevatureSocialNetworkApplication.class)
public class CompanyControllerTest {
    
    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
    
    private MockMvc mockMvc;
    
    @Mock
    private CompanyDao mockComDao;
    @Mock
    private LoginOperations service;

    @InjectMocks
    private CompanyController companyController;
    
    // Object we want to inject into companies
    List<Company> companies;
    Company company1;
    Company company2;
    
    MockHttpSession validSession;
    MockHttpSession emptySession;
    
    @Before
    public void setup() throws Exception {
        
        MockitoAnnotations.initMocks(this);
        
        this.mockMvc = MockMvcBuilders.standaloneSetup(companyController).build();
        
        when(service.isLoggedIn()).thenReturn(true);
        
        
        emptySession = new MockHttpSession();
        validSession = new MockHttpSession();
        validSession.setAttribute("id", 1);
        validSession.setAttribute("auth", new Boolean(false));
        
        // Setup all the companies info
        companies = new ArrayList<>();
        
        Location location = new Location("LA", 123.12, 123.12);
        location.setLocationId(1L);
        
        company1 = new Company(location, "Bobs");
        company1.setCompanyId(1L);
        
        company2 = new Company(location, "Brix Phones");
        company2.setCompanyId(2L);
        
        companies.add(company1);
        
        companies.add(company2);
    }
    
    @Test
    public void requestsOnlyCompleteWithValidSessions() throws Exception {
        
        when(service.isLoggedIn()).thenReturn(false);

        // When the method calls .readAll(), mockito will not call it
        // and return the companies list instead
        when(mockComDao.readAll()).thenReturn(companies);
        when(mockComDao.read(anyLong())).thenReturn(company1);
        when(mockComDao.create(any(CompanyForm.class))).thenReturn(true);
        when(mockComDao.deleteById(anyLong())).thenReturn(true);
        
        // Now call the api
        RequestBuilder rb1 = get("/companies").session(emptySession).accept(contentType);
        RequestBuilder rb2 = get("/companies/1").session(emptySession).accept(contentType);
        RequestBuilder rb3 = post("/companies").session(emptySession).accept(contentType);
        RequestBuilder rb4 = delete("/companies/1").session(emptySession).accept(contentType);
        RequestBuilder rb5 = get("/companies").session(emptySession).accept(contentType);
        
        mockMvc.perform(rb1).andExpect(status().isUnauthorized());
        
    }
    
    @Test
    public void returnAllCompanies() throws Exception {
        
        // When the method calls .readAll(), mockito will not call it
        // and return the companies list instead
        when(mockComDao.readAll()).thenReturn(companies);
        
        // Now call the api
        MockHttpServletRequestBuilder rb = get("/companies").session(validSession);
        
        // Test passes
        mockMvc.perform(rb).andExpect(status().isOk()).andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].companyId",
                        Matchers.is(companies.get(0).getCompanyId().intValue())))
                .andExpect(jsonPath("$[0].companyName",
                        Matchers.is(companies.get(0).getCompanyName())))
                .andExpect(jsonPath("$[0].location", Matchers.notNullValue()))
                .andExpect(jsonPath("$[1].companyId",
                        Matchers.is(companies.get(1).getCompanyId().intValue())))
                .andExpect(jsonPath("$[1].companyName",
                        Matchers.is(companies.get(1).getCompanyName())))
                .andExpect(jsonPath("$[1].location", Matchers.notNullValue()));
        
    }
    
    @Test
    public void returnSingleCompany() throws Exception {
        
        when(mockComDao.read(anyLong())).thenReturn(company1);
        
        RequestBuilder rb = get("/companies/" + company1.getCompanyId()).session(validSession)
                .accept(contentType);
        
        mockMvc.perform(rb).andExpect(status().isOk()).andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.companyId", Matchers.is(company1.getCompanyId().intValue())))
                .andExpect(jsonPath("$.companyName", Matchers.is(company1.getCompanyName())))
                .andExpect(jsonPath("$.location", Matchers.notNullValue()));
        
    }
    
    @Test
    public void createCompanyPass() throws Exception {
        
        when(mockComDao.create(any(CompanyForm.class))).thenReturn(true);
        
        RequestBuilder rb = post("/companies").session(validSession)
                .param("locationId", company1.getLocation().getLocationId() + "")
                .param("companyName", company1.getCompanyName());
        
        mockMvc.perform(rb).andExpect(status().isOk()).andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.message", Matchers.is("Success")))
                .andExpect(jsonPath("$.success", Matchers.is(true)));
    }
    
    @Test
    public void createCompanyFail() throws Exception {
        
        when(mockComDao.create(any(CompanyForm.class))).thenReturn(true);
        
        RequestBuilder rb = post("/companies").session(validSession);
        
        mockMvc.perform(rb).andExpect(status().isOk()).andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.success", Matchers.is(false)));
    }
    
    @Test
    public void deleteCompany() throws Exception {
        
        when(mockComDao.deleteById(anyLong())).thenReturn(true);
        
        RequestBuilder rb = delete("/companies/" + company1.getCompanyId()).session(validSession);
        
        mockMvc.perform(rb).andExpect(status().isOk()).andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.message", Matchers.is("Success")))
                .andExpect(jsonPath("$.success", Matchers.is(true)));
    }
    
}
