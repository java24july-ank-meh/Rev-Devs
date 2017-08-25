package com.revature.application.dao.beans;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
public class Company {

	@Id
	@SequenceGenerator(name="companySequence", sequenceName="company_id_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE ,generator="companySequence")
	@NotNull
	private long companyId;

	@ManyToOne
	@JoinColumn(name = "locationId")
	@NotNull
	private Location location;

	@Column
	@NotNull
	private String companyName;

	@OneToMany(cascade = { CascadeType.ALL })
	@JoinColumn(name = "companyId")
	private Set<Employee> employees = new HashSet<Employee>();

	public Company() {
	}

	public Company(Location location, String companyName) {
		super();
		this.location = location;
		this.companyName = companyName;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
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
		return "Company [companyId=" + companyId + ", location=" + location + ", companyName=" + companyName + "]";
	}

}
