package com.revature.dao;

import javax.persistence.*;

@Entity
public class Company {
	
	@Id
	private long company_id;
	
	private long location_id;
	
	private String company_name;
	
	public Company() {
		// TODO Auto-generated constructor stub
	}

	public Company(long company_id, long location_id, String company_name) {
		super();
		this.company_id = company_id;
		this.location_id = location_id;
		this.company_name = company_name;
	}

	@Override
	public String toString() {
		return "Company [company_id=" + company_id + ", location_id=" + location_id + ", company_name=" + company_name
				+ "]";
	}

	public long getCompany_id() {
		return company_id;
	}

	public void setCompany_id(long company_id) {
		this.company_id = company_id;
	}

	public long getLocation_id() {
		return location_id;
	}

	public void setLocation_id(long location_id) {
		this.location_id = location_id;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

}
