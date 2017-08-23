package com.revature.application.restControllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revature.application.beans.Greeting;
import com.revature.application.dao.Location;

@RestController
@RequestMapping("/locations")
public class LocationController {

	private Location location = new Location(1, "city", 12.12, 12.12);
	
	/*
	 * All GET requests
	 */
	@RequestMapping(path = "", method = RequestMethod.GET)
	public Collection<Location> readAllLocations() {
		// Get all the locations from db
		List<Location> locations = new ArrayList<>();
		locations.add(location);
		return locations;
	}

	@RequestMapping(path = "/{locationId}", method = RequestMethod.GET)
	public Location readLocationById(@PathVariable long locationId) {
		// Get single location from db by id
		return location;
	}

	/*
	 * All POST requests
	 */
	@RequestMapping(path = "", method = RequestMethod.POST)
	public Location createLocation(/* Take in a location object */) {
		// Add a new location to the db
		return location;
	}

	/*
	 * All DELETE requests
	 */
	@RequestMapping(path = "/{locationId}", method = RequestMethod.DELETE)
	public Location deleteLocation(@PathVariable long locationId) {
		// Delete a single location
		return location;
	}

}
