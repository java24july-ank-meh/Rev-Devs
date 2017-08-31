package com.revature.application.restControllers;

import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revature.application.beans.RequestStatus;
import com.revature.application.dao.HotSpotDao;
import com.revature.application.dao.LocationDao;
import com.revature.application.dao.PostDao;
import com.revature.application.dao.beans.Employee;
import com.revature.application.dao.beans.HotSpot;
import com.revature.application.dao.beans.Location;
import com.revature.application.dao.beans.Post;
import com.revature.application.dao.beans.forms.HotSpotForm;
import com.revature.application.dao.beans.forms.LocationForm;
import com.revature.application.services.LoginOperations;

@RestController
@RequestMapping("/locations")
public class LocationController {
    
    @Autowired
    LocationDao locationDAO;
    
    @Autowired
    HotSpotDao hotSpotDAO;
    
    @Autowired
    PostDao postDAO;
    
    @Autowired
    LoginOperations loginService;
    
    /*
     * All GET requests
     */
    @RequestMapping(path = "", method = RequestMethod.GET)
    public ResponseEntity<List<Location>> readAllLocations() {
        return new ResponseEntity<>(locationDAO.readAll(), HttpStatus.OK);
        
    }
    
    @RequestMapping(path = "/{locationId}", method = RequestMethod.GET)
    public ResponseEntity<Location> readLocationById(@PathVariable long locationId) {
        return new ResponseEntity<>(locationDAO.read(locationId), HttpStatus.OK);
    }
    
    @RequestMapping(path = "/{locationId}/hotspots", method = RequestMethod.GET)
    public ResponseEntity<List<HotSpot>> readAllHotSpots(@PathVariable long locationId) {
        // Read the hotspot for a post
        if (loginService.isLoggedIn()) {
            return new ResponseEntity<List<HotSpot>>(
                    hotSpotDAO.readAllHotSpotsByLocationId(locationId), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }  
    }
    
    @RequestMapping(path = "/{locationId}/posts", method = RequestMethod.GET)
    public ResponseEntity<Set<Post>> readAllPosts(@PathVariable long locationId) {
        return new ResponseEntity<Set<Post>>(
                locationDAO.read(locationId).getPosts(), HttpStatus.OK);
    }
    
    @RequestMapping(path = "/{locationId}/users", method = RequestMethod.GET)
    public ResponseEntity<Set<Employee>> readAllUsers(@PathVariable long locationId) {
        // Read the hotspot for a post
        return new ResponseEntity<Set<Employee>>(
                locationDAO.read(locationId).getEmployees(), HttpStatus.OK);
    }
    
    @RequestMapping(path = "/name/{location}", method = RequestMethod.GET)
    public ResponseEntity<Location> readLocationByName(@PathVariable String location) {
        return new ResponseEntity<>(locationDAO.read(location), HttpStatus.OK);
    }
    
    /*
     * All POST requests
     */
    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<RequestStatus> createLocation(@Valid LocationForm locationForm,
            BindingResult bindingResult) {
        
        if (loginService.isLoggedIn()) {
            if (!bindingResult.hasErrors()) {
                locationDAO.create(locationForm);
                return new ResponseEntity<>(new RequestStatus(), HttpStatus.OK);
            }
            return new ResponseEntity<>(new RequestStatus(false, "Failed to create new location"),
                    HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        
    }
    
    @RequestMapping(path = "/{locationId}/hotspots", method = RequestMethod.POST)
    public ResponseEntity<RequestStatus> createHotSpot(@Valid HotSpotForm hotSpotForm,
            BindingResult bindingResult, @PathVariable long locationId) {
        // Read the hotspot for a post
        if (loginService.isLoggedIn()) {
            if (!bindingResult.hasErrors()) {
                hotSpotDAO.createHotSpot(hotSpotForm);
                return new ResponseEntity<>(new RequestStatus(), HttpStatus.OK);
            }
            return new ResponseEntity<>(new RequestStatus(false, "Failed to create new hot spot"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        
    }
    
    /*
     * All DELETE requests
     */
    @RequestMapping(path = "/{locationId}", method = RequestMethod.DELETE)
    public ResponseEntity<RequestStatus> deleteLocation(@PathVariable long locationId) {
        // Delete a single location
        if (loginService.isLoggedIn()) {
            locationDAO.deleteById(locationId);
            return new ResponseEntity<>(new RequestStatus(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        
    
    }
    
}
