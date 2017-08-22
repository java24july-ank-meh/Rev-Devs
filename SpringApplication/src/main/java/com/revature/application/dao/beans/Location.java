package com.revature.application.dao.beans;

import java.util.Set;

import javax.persistence.*;

@Entity
public class Location {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long locationId;

	@Column
	private String city;

	@Column(nullable = false)
	private double longitude;

	@Column(nullable = false)
	private double lattitude;

	@OneToMany(cascade = { CascadeType.ALL })
	@JoinColumn(name = "locationId")
	private Set<Employee> employees;

	@OneToMany(cascade = { CascadeType.ALL })
	@JoinColumn(name = "locationId")
	private Set<Company> companies;

	@OneToMany(cascade = { CascadeType.ALL })
	@JoinColumn(name = "locationId")
	private Set<Post> posts;

	public Location() {
	}

	public Location(long locationId, String city, double longitude, double lattitude) {
		super();
		this.locationId = locationId;
		this.city = city;
		this.longitude = longitude;
		this.lattitude = lattitude;
	}

	public long getLocationId() {
		return locationId;
	}

	public void setLocationId(long locationId) {
		this.locationId = locationId;
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
		return "Location [locationId=" + locationId + ", city=" + city + ", longitude=" + longitude + ", lattitude="
				+ lattitude + "]";
	}

}
