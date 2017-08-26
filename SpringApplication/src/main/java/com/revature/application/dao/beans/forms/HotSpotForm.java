package com.revature.application.dao.beans.forms;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class HotSpotForm {
    
    @NotNull
    @DecimalMin("-180.0")
    @DecimalMax("180.0")
    private double longitude;
    
    @NotNull
    @DecimalMin("-180.0")
    @DecimalMax("180.0")
    private double lattitude;
    
    @NotNull
    @Min(0)
    private long locationId;
    
    public HotSpotForm() {
        super();
    }
    
    public HotSpotForm(double longitude, double lattitude, long locationId) {
        super();
        this.longitude = longitude;
        this.lattitude = lattitude;
        this.locationId = locationId;
    }
    
    public double getLongitude() {
        return longitude;
    }
    
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    
    public double getLattitude() {
        return lattitude;
    }
    
    public void setLattitude(double lattitude) {
        this.lattitude = lattitude;
    }
    
    public long getLocationId() {
        return locationId;
    }
    
    public void setLocationId(long locationId) {
        this.locationId = locationId;
    }
    
    @Override
    public String toString() {
        return "HotSpotForm [longitude=" + longitude + ", lattitude=" + lattitude + ", locationId="
                + locationId + "]";
    }
    
}
