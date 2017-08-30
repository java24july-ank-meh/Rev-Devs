package com.revature.application.dao.beans.forms;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PostForm {
    
    @NotNull
    @Min(0)
    private Long locationId;
    
    @Min(0)
    private Long employeeId;
    
    @NotNull
    @Min(0)
    private Long typeId;
    
    @NotNull
    @Size(min = 1, max = 250)
    private String content;
    
    public PostForm() {
    }
    
    public PostForm(Long locationId, Long employeeId, Long typeId, String content) {
        super();
        this.locationId = locationId;
        this.employeeId = employeeId;
        this.typeId = typeId;
        this.content = content;
    }
    
    public Long getLocationId() {
        return locationId;
    }
    
    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }
    
    public Long getEmployeeId() {
        return employeeId;
    }
    
    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }
    
    public Long getTypeId() {
        return typeId;
    }
    
    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    @Override
    public String toString() {
        return "PostForm [locationId=" + locationId + ", employeeId=" + employeeId + ", typeId="
                + typeId + ", content=" + content + "]";
    }
    
}
