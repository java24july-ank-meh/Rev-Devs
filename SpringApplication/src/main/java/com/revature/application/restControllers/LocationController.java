package com.revature.application.restControllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revature.application.beans.RequestStatus;
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
	public List<Location> readAllLocations() {
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
	public RequestStatus createLocation(@Valid Location location, BindingResult bindingResult) {
			
		if (!bindingResult.hasErrors()) {
			locationDAO.create(location);
			return new RequestStatus();
		}
		return new RequestStatus(false, "Failed to create new location");
	}

	/*
	 * All DELETE requests
	 */
	@RequestMapping(path = "/{locationId}", method = RequestMethod.DELETE)
	public RequestStatus deleteLocation(@PathVariable long locationId) {
		// Delete a single location
		locationDAO.deleteById(locationId);
		return new RequestStatus();
	}

}
