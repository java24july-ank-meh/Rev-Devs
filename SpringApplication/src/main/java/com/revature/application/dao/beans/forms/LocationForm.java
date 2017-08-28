package com.revature.application.dao.beans.forms;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class LocationForm {
    
    @NotNull
    @Size(min = 1, max = 32)
    private String city;
    
    @NotNull
    @DecimalMin("-180.0")
    @DecimalMax("180.0")
    private Double longitude;
    
    @NotNull
    @DecimalMin("-180.0")
    @DecimalMax("180.0")
    private Double lattitude;
    
    public LocationForm() {
    }
    
    public LocationForm(String city, double longitude, double lattitude) {
        super();
        this.city = city;
        this.longitude = longitude;
        this.lattitude = lattitude;
    }
    
    public String getCity() {
        return city;
    }
    
    public void setCity(String city) {
        this.city = city;
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
    
    @Override
    public String toString() {
        return "LocationForm [city=" + city + ", longitude=" + longitude + ", lattitude="
                + lattitude + "]";
    }
    
}
