package com.revature.application.dao.beans;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Company {
    
    @Id
    @SequenceGenerator(name = "companySequence", sequenceName = "company_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "companySequence")
    private Long companyId;
    
    @ManyToOne
    @JoinColumn(name = "locationId")
    private Location location;
    
    @Column
    private String companyName;
    
    @OneToMany(fetch=FetchType.EAGER, cascade = { CascadeType.ALL })
    @JoinColumn(name = "companyId")
    @JsonIgnore
    private Set<Employee> employees = new HashSet<Employee>();
    
    public Company() {
    }
    
    public Company(Location location, String companyName) {
        super();
        this.location = location;
        this.companyName = companyName;
    }
    
    public Long getCompanyId() {
        return companyId;
    }
    
    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
    
    public Location getLocation() {
        return location;
    }
    
    public void setLocation(Location location) {
        this.location = location;
    }
    
    public String getCompanyName() {
        return companyName;
    }
    
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    
    public Set<Employee> getEmployees() {
        return employees;
    }
    
    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }
    
    @Override
    public String toString() {
        return "Company [companyId=" + companyId + ", location=" + location + ", companyName="
                + companyName + "]";
    }
    
}
