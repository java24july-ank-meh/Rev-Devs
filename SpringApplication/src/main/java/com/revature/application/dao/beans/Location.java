package com.revature.application.dao.beans;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
public class Location {
    
    @Id
    @SequenceGenerator(name = "locationSequence", sequenceName = "location_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "locationSequence")
    private Long locationId;
    
    @Column
    private String city;
    
    @Column(nullable = false)
    private Double longitude;
    
    @Column(nullable = false)
    private Double lattitude;
    
    @OneToMany(cascade = { CascadeType.ALL })
    @JoinColumn(name = "locationId")
    private Set<Employee> employees = new HashSet<>();
    
    @OneToMany(cascade = { CascadeType.ALL })
    @JoinColumn(name = "locationId")
    private Set<Company> companies = new HashSet<>();
    
    @OneToMany(cascade = { CascadeType.ALL })
    @JoinColumn(name = "locationId")
    private Set<Post> posts = new HashSet<>();
    
    public Location() {
    }
    
    public Location(String city, Double longitude, Double lattitude) {
        super();
        this.city = city;
        this.longitude = longitude;
        this.lattitude = lattitude;
    }
    
    public Long getLocationId() {
        return locationId;
    }
    
    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }
    
    public String getCity() {
        return city;
    }
    
    public void setCity(String city) {
        this.city = city;
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
    
    public Set<Employee> getEmployees() {
        return employees;
    }
    
    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }
    
    public Set<Company> getCompanies() {
        return companies;
    }
    
    public void setCompanies(Set<Company> companies) {
        this.companies = companies;
    }
    
    public Set<Post> getPosts() {
        return posts;
    }
    
    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }
    
    @Override
    public String toString() {
        return "Location [locationId=" + locationId + ", city=" + city + ", longitude=" + longitude
                + ", lattitude=" + lattitude + "]";
    }
    
}
