package com.revature.application.dao.beans.forms;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class HotSpotForm {
    
    @NotNull
    @DecimalMin("-180.0")
    @DecimalMax("180.0")
    private Double longitude;
    
    @NotNull
    @DecimalMin("-180.0")
    @DecimalMax("180.0")
    private Double lattitude;
    
    @NotNull
    @Min(0)
    private Long locationId;
    
    public HotSpotForm() {
        super();
    }
    
    public HotSpotForm(Double longitude, Double lattitude, Long locationId) {
        super();
        this.longitude = longitude;
        this.lattitude = lattitude;
        this.locationId = locationId;
    }
    
    public Double getLongitude() {
        return longitude;
    }
    
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
    
    public Double getLattitude() {
        return lattitude;
    }
    
    public void setLattitude(Double lattitude) {
        this.lattitude = lattitude;
    }
    
    public Long getLocationId() {
        return locationId;
    }
    
    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }
    
    @Override
    public String toString() {
        return "HotSpotForm [longitude=" + longitude + ", lattitude=" + lattitude + ", locationId="
                + locationId + "]";
    }
    
}
