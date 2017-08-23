package com.revature.application.restControllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revature.application.dao.CompanyDao;
import com.revature.application.dao.beans.Company;

@RestController
@RequestMapping("/companies")
public class CompanyController {

	@Autowired
	CompanyDao companyDAO;
	
	/*
	 * All GET requests
	 */
	@RequestMapping(path = "", method = RequestMethod.GET)
	public List<Company> readAllCompanies() {
		// Get all the companies from db
		return companyDAO.readAll();
	}

	@RequestMapping(path = "/{companyId}", method = RequestMethod.GET)
	public Company readCompanyById(@PathVariable long companyId) {
		// Get single company from db by id
		return companyDAO.read(companyId);
	}

	/*
	 * All POST requests
	 */
	@RequestMapping(path = "", method = RequestMethod.POST)
	public boolean createCompany(/* Take in a company object */) {
		// Add a new company to the db
		return true;
	}

	/*
	 * All DELETE requests
	 */
	@RequestMapping(path = "/{companyId}", method = RequestMethod.DELETE)
	public boolean deleteLocation(@PathVariable long companyId) {
		// Delete a single location
		return companyDAO.deleteById(companyId);
	}

}
