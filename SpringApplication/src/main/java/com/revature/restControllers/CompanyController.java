package com.revature.restControllers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revature.beans.Greeting;

@RestController
@RequestMapping("/companies")
public class CompanyController {

	/*
	 * All GET requests
	 */
	@RequestMapping(path = "", method = RequestMethod.GET)
	public Greeting readAllCompanies() {
		// Get all the companies from db
		return new Greeting(1, "Returning all companies");
	}

	@RequestMapping(path = "/{companyId}", method = RequestMethod.GET)
	public Greeting readCompanyById(@PathVariable long companyId) {
		// Get single company from db by id
		return new Greeting(1, "Returning single company");
	}

	/*
	 * All POST requests
	 */
	@RequestMapping(path = "", method = RequestMethod.POST)
	public Greeting createCompany(/* Take in a company object */) {
		// Add a new company to the db
		return new Greeting(1, "Added a single company");
	}

	/*
	 * All DELETE requests
	 */
	@RequestMapping(path = "/{companyId}", method = RequestMethod.DELETE)
	public Greeting deleteLocation(@PathVariable long companyId) {
		// Delete a single location
		return new Greeting(1, "Deleted single company");
	}

}
