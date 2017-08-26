package com.revature.application.dao.beans.forms;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CompanyForm {
    
    @NotNull
    @Min(0)
    private long locationId;
    
    @NotNull
    @Size(min = 1, max = 32)
    private String companyName;
    
    public CompanyForm() {
        super();
    }
    
    public CompanyForm(long locationId, String companyName) {
        super();
        this.locationId = locationId;
        this.companyName = companyName;
    }
    
    public long getLocationId() {
        return locationId;
    }
    
    public void setLocationId(long locationId) {
        this.locationId = locationId;
    }
    
    public String getCompanyName() {
        return companyName;
    }
    
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    
}
