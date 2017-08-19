package com.revature.restControllers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.beans.Greeting;

@RestController
@RequestMapping("/locations")
public class LocationController {

	/*
	 * All GET requests
	 */
	@RequestMapping("")
	public Greeting getAllLocations() {
		// Get all the locations from db
		return new Greeting(1, "Returning all locations");
	}
	
	@RequestMapping("/{locationId}")
	public Greeting getLocationById(@PathVariable("locationId") long locationId) {
		// Get single location from db by id
		return new Greeting(1, "Returning single location");
	}
	
	/*
	 * All POST requests
	 */
	@RequestMapping("")
	public Greeting createLocation(/* Take in a location object */) {
		// Add a new location to the db
		return new Greeting(1, "Added a single location");
	}
	
	/*
	 * All DELETE requests
	 */
	@RequestMapping("/{locationId}")
	public Greeting deleteLocation(@PathVariable("locationId") long locationId) {
		// Delete a single location
		return new Greeting(1, "Deleted single location");
	}	
	
}
