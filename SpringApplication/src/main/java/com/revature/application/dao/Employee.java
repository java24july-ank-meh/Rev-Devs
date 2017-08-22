package com.revature.application.dao;

import java.util.Set;

import javax.persistence.*;

@Entity
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long employeeId;

	@ManyToOne
	@JoinColumn(name = "locationId")
	private Location location;

	@ManyToOne
	@JoinColumn(name = "companyId")
	private Company company;

	@Column(nullable = false, unique = true)
	private String username;

	@Column(nullable = false)
	private String password;

	@Column
	private String email;

	@Column
	private String fname;

	@Column
	private String lname;

	@OneToMany(cascade = { CascadeType.ALL })
	@JoinColumn(name = "employeeId")
	private Set<Post> posts;

	@OneToMany(cascade = { CascadeType.ALL })
	@JoinColumn(name = "employeeId")
	private Set<PostComment> comments;

	public Employee() {
	}

	public Employee(Location location, Company company, String username, String password, String email, String fname,
			String lname) {
		super();
		this.location = location;
		this.company = company;
		this.username = username;
		this.password = password;
		this.email = email;
		this.fname = fname;
		this.lname = lname;
	}

	public long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(long employeeId) {
		this.employeeId = employeeId;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public Set<Post> getPosts() {
		return posts;
	}

	public void setPosts(Set<Post> posts) {
		this.posts = posts;
	}

	public Set<PostComment> getComments() {
		return comments;
	}

	public void setComments(Set<PostComment> comments) {
		this.comments = comments;
	}

	@Override
	public String toString() {
		return "Employee [employeeId=" + employeeId + ", location=" + location + ", company=" + company + ", username="
				+ username + ", password=" + password + ", email=" + email + ", fname=" + fname + ", lname=" + lname
				+ "]";
	}
	
	

}
