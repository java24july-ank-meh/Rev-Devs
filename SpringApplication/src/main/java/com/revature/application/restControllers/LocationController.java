package com.revature.application.restControllers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revature.application.beans.Greeting;

@RestController
@RequestMapping("/locations")
public class LocationController {

	/*
	 * All GET requests
	 */
	@RequestMapping(path = "", method = RequestMethod.GET)
	public Greeting readAllLocations() {
		// Get all the locations from db
		return new Greeting(1, "Returning all locations");
	}

	@RequestMapping(path = "/{locationId}", method = RequestMethod.GET)
	public Greeting readLocationById(@PathVariable long locationId) {
		// Get single location from db by id
		return new Greeting(1, "Returning single location");
	}

	/*
	 * All POST requests
	 */
	@RequestMapping(path = "", method = RequestMethod.POST)
	public Greeting createLocation(/* Take in a location object */) {
		// Add a new location to the db
		return new Greeting(1, "Added a single location");
	}

	/*
	 * All DELETE requests
	 */
	@RequestMapping(path = "/{locationId}", method = RequestMethod.DELETE)
	public Greeting deleteLocation(@PathVariable long locationId) {
		// Delete a single location
		return new Greeting(1, "Deleted single location");
	}

}
