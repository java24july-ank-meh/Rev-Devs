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
import com.revature.application.dao.HotSpotDao;
import com.revature.application.dao.LocationDao;
import com.revature.application.dao.beans.HotSpot;
import com.revature.application.dao.beans.Location;
import com.revature.application.dao.beans.forms.HotSpotForm;
import com.revature.application.dao.beans.forms.LocationForm;

@RestController
@RequestMapping("/locations")
public class LocationController {
	
	@Autowired
	LocationDao locationDAO;
	
	@Autowired
	HotSpotDao hotSpotDAO;
	
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
	
	@RequestMapping(path = "/{locationId}/hotspots", method = RequestMethod.GET)
	public List<HotSpot> readAllHotSpots(@PathVariable long locationId) {
		// Read the hotspot for a post
		return hotSpotDAO.readAllHotSpotsByLocationId(locationId);
	}
	
	/*
	 * All POST requests
	 */
	@RequestMapping(path = "", method = RequestMethod.POST)
	public RequestStatus createLocation(@Valid LocationForm locationForm, BindingResult bindingResult) {
			
		if (!bindingResult.hasErrors()) {
			locationDAO.create(locationForm);
			return new RequestStatus();
		}
		return new RequestStatus(false, "Failed to create new location");
	}
	
	@RequestMapping(path = "/{locationId}/hotspots", method = RequestMethod.POST)
	public RequestStatus createHotSpot(@Valid HotSpotForm hotSpotForm, BindingResult bindingResult, @PathVariable long locationId) {
		// Read the hotspot for a post
		if (!bindingResult.hasErrors()) {
			hotSpotDAO.createHotSpot(hotSpotForm);
			return new RequestStatus();
		}
		
		return new RequestStatus(false, "Failed to create new hot spot");
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
