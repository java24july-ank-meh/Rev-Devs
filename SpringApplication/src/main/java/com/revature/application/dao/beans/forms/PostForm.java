package com.revature.application.dao.beans.forms;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PostForm {
    
    @NotNull
    @Min(0)
    private long locationId;
    
    @NotNull
    @Min(0)
    private long employeeId;
    
    @NotNull
    @Min(0)
    private long typeId;
    
    @NotNull
    @Size(min = 1, max = 250)
    private String content;
    
    public PostForm() {
    }
    
    public PostForm(long locationId, long employeeId, long typeId, String content) {
        super();
        this.locationId = locationId;
        this.employeeId = employeeId;
        this.typeId = typeId;
        this.content = content;
    }
    
    public long getLocationId() {
        return locationId;
    }
    
    public void setLocationId(long locationId) {
        this.locationId = locationId;
    }
    
    public long getEmployeeId() {
        return employeeId;
    }
    
    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }
    
    public long getTypeId() {
        return typeId;
    }
    
    public void setTypeId(long typeId) {
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
