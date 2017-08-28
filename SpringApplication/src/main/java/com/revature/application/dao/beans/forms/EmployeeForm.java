package com.revature.application.dao.beans.forms;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class EmployeeForm {
    
    @Min(0)
    private Long locationId;
    
    @Min(0)
    private Long companyId;
    
    @NotNull
    @Size(min = 5, max = 32)
    private String username;
    
    @NotNull
    @Size(min = 7, max = 32)
    private String password;
    
    @NotNull
    private String email;
    
    @NotNull
    @Size(min = 1)
    private String fname;
    
    @NotNull
    @Size(min = 1)
    private String lname;
    
    public EmployeeForm() {
    }
    
    public EmployeeForm(Long locationId, Long companyId, String username, String password,
            String email, String fname, String lname) {
        super();
        this.locationId = locationId;
        this.companyId = companyId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.fname = fname;
        this.lname = lname;
    }
    
    public Long getLocationId() {
        return locationId;
    }
    
    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }
    
    public Long getCompanyId() {
        return companyId;
    }
    
    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getFname() {
        return fname;
    }
    
    public void setFname(String fname) {
        this.fname = fname;
    }
    
    public String getLname() {
        return lname;
    }
    
    public void setLname(String lname) {
        this.lname = lname;
    }
    
    @Override
    public String toString() {
        return "EmployeeForm [locationId=" + locationId + ", companyId=" + companyId + ", username="
                + username + ", password=" + password + ", email=" + email + ", fname=" + fname
                + ", lname=" + lname + "]";
    }
    
}
