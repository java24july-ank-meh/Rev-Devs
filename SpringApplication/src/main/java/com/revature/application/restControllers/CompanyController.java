package com.revature.application.restControllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revature.application.beans.RequestStatus;
import com.revature.application.dao.CompanyDao;
import com.revature.application.dao.beans.Company;
import com.revature.application.dao.beans.forms.CompanyForm;
import com.revature.application.services.SafeUserOperations;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    
    @Autowired
    CompanyDao companyDAO;
    
    @Autowired
    SafeUserOperations userOperations;
    
    /*
     * All GET requests
     */
    @RequestMapping(path = "", method = RequestMethod.GET)
    public ResponseEntity<List<Company>> readAllCompanies() {
        // Get all the companies from db
        if (userOperations.isValidSession()) {
            return new ResponseEntity<>(companyDAO.readAll(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
    
    @RequestMapping(path = "/{companyId}", method = RequestMethod.GET)
    public ResponseEntity<Company> readCompanyById(@PathVariable long companyId) {
        // Get single company from db by id
        if (userOperations.isValidSession()) {
            return new ResponseEntity<>(companyDAO.read(companyId), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
    
    /*
     * All POST requests
     */
    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<RequestStatus> createCompany(@Valid CompanyForm companyForm,
            BindingResult bindingResult) {
        // Add a new company to the db
        
        if (userOperations.isValidSession()) {
            if (!bindingResult.hasErrors()) {
                companyDAO.create(companyForm);
                return new ResponseEntity<>(new RequestStatus(), HttpStatus.OK);
            }
            return new ResponseEntity<>(new RequestStatus(false, "Failed to create new company"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
    
    /*
     * All DELETE requests
     */
    @RequestMapping(path = "/{companyId}", method = RequestMethod.DELETE)
    public ResponseEntity<RequestStatus> deleteLocation(@PathVariable long companyId) {
        // Delete a single location
        if (userOperations.isValidSession()) {
            companyDAO.deleteById(companyId);
            return new ResponseEntity<>(new RequestStatus(), HttpStatus.OK); 
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); 
    }
    
}
