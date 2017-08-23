package com.revature.application.restControllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revature.application.dao.LocationDao;
import com.revature.application.dao.beans.Location;

@RestController
@RequestMapping("/locations")
public class LocationController {
	
	@Autowired
	LocationDao locationDAO;
	
	/*
	 * All GET requests
	 */
	@RequestMapping(path = "", method = RequestMethod.GET)
	public Set<Location> readAllLocations() {
		return locationDAO.readAll();
	}

	@RequestMapping(path = "/{locationId}", method = RequestMethod.GET)
	public Location readLocationById(@PathVariable long locationId) {
		return locationDAO.read(locationId);
	}

	/*
	 * All POST requests
	 */
	@RequestMapping(path = "", method = RequestMethod.POST)
	public boolean createLocation(/* Take in a location object */) {
		// Add a new location to the db
		return true;
	}

	/*
	 * All DELETE requests
	 */
	@RequestMapping(path = "/{locationId}", method = RequestMethod.DELETE)
	public boolean deleteLocation(@PathVariable long locationId) {
		// Delete a single location
		return locationDAO.deleteById(locationId);
	}

}
