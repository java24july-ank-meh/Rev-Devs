package com.revature.dao;

import javax.persistence.*;

@Entity
public class User {

	@Id
	private long user_id;
	
	private long location_id;
	
	private long company_id;
	
	private String user_name;
	
	private String pass_word;
	
	private String email;
	
	private String first_name;
	
	private String last_name;	
	
	public User() {
		// TODO Auto-generated constructor stub
	}

	public User(long user_id, long location_id, long company_id, String user_name, String pass_word, String email,
			String first_name, String last_name) {
		super();
		this.user_id = user_id;
		this.location_id = location_id;
		this.company_id = company_id;
		this.user_name = user_name;
		this.pass_word = pass_word;
		this.email = email;
		this.first_name = first_name;
		this.last_name = last_name;
	}

	@Override
	public String toString() {
		return "User [user_id=" + user_id + ", location_id=" + location_id + ", company_id=" + company_id
				+ ", user_name=" + user_name + ", pass_word=" + pass_word + ", email=" + email + ", first_name="
				+ first_name + ", last_name=" + last_name + "]";
	}

	public long getUser_id() {
		return user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	public long getLocation_id() {
		return location_id;
	}

	public void setLocation_id(long location_id) {
		this.location_id = location_id;
	}

	public long getCompany_id() {
		return company_id;
	}

	public void setCompany_id(long company_id) {
		this.company_id = company_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getPass_word() {
		return pass_word;
	}

	public void setPass_word(String pass_word) {
		this.pass_word = pass_word;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	
}
