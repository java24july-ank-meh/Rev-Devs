package com.revature.dao;

import javax.persistence.*;

@Entity
public class Location {

	@Id
	private long location_id;
	
	private String city;
	
	private double longitude;
	
	private double lattitude;
	
	public Location() {
		// TODO Auto-generated constructor stub
	}

	public Location(long location_id, String city, double longitude, double lattitude) {
		super();
		this.location_id = location_id;
		this.city = city;
		this.longitude = longitude;
		this.lattitude = lattitude;
	}

	@Override
	public String toString() {
		return "Location [location_id=" + location_id + ", city=" + city + ", longitude=" + longitude + ", lattitude="
				+ lattitude + "]";
	}

	public long getLocation_id() {
		return location_id;
	}

	public void setLocation_id(long location_id) {
		this.location_id = location_id;
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

}
