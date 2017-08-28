package com.revature.application.dao.implementations;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.revature.application.RevatureSocialNetworkApplication;
import com.revature.application.dao.LocationDao;
import com.revature.application.dao.beans.Location;
import com.revature.application.dao.beans.forms.LocationForm;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RevatureSocialNetworkApplication.class)
@Transactional
public class LocationDaoImplTest {
    
    @Autowired
    private SessionFactory sf;
    
    @Autowired
    private LocationDao locationDAO;
    
    // Objects we want to persist in db
    Location location1 = null;
    Location location2 = null;
    Location location3 = null;
    
    @Before
    public void setup() {
        
        Session session = sf.getCurrentSession();
        
        location1 = new Location("LA", 123.12, 123.12);
        location2 = new Location("SF", 123.12, 123.12);
        location3 = new Location("NY", 123.12, 123.12);
        
        session.saveOrUpdate(location1);
        session.saveOrUpdate(location2);
        // The last location3 will be used to persist using our api
        
    }
    
    @Test
    public void createMethodMustSaveToDb() {
        
        Session session = sf.getCurrentSession();
        
        LocationForm locationForm = new LocationForm(location3.getCity(), location3.getLongitude(),
                location3.getLattitude());
        
        locationDAO.create(locationForm);
        
        String query = "from Location location where location.city = :city and location.longitude = :longitude and location.lattitude = :lattitude";
        Location newLocation = (Location) session.createQuery(query)
                .setParameter("city", location3.getCity())
                .setParameter("longitude", location3.getLongitude())
                .setParameter("lattitude", location3.getLattitude()).uniqueResult();
        
        assertTrue("Location object must be persisted to DB", newLocation != null);
        assertTrue(newLocation.getCity().equals(location3.getCity()));
        assertTrue(newLocation.getLongitude().equals(location3.getLongitude()));
        assertTrue(newLocation.getLattitude().equals(location3.getLattitude()));
    }
    
    @Test
    public void readGetsTheProperLocationObject() {
        
        Session session = sf.getCurrentSession();
        
        Location newLocation = locationDAO.read(location1.getLocationId());
        
        assertTrue("Location object must not be null", newLocation != null);
        assertTrue(newLocation.getCity().equals(location1.getCity()));
        assertTrue(newLocation.getLongitude().equals(location1.getLongitude()));
        assertTrue(newLocation.getLattitude().equals(location1.getLattitude()));
        
    }
    
    @Test
    public void readAllGetsAllLocationObjects() {
        
        Session session = sf.getCurrentSession();
        
        List<Location> locations = locationDAO.readAll();
        
        assertTrue("Companies must not be null", locations != null);
        
        locations.sort((item1, item2) -> {
            if (item1.getLocationId() < item2.getLocationId()) {
                return -1;
            } else if (item1.getLocationId() > item2.getLocationId()) {
                return 1;
            } else {
                return 0;
            }
        });
        
        assertTrue(locations.size() == 2);
        
        assertTrue(locations.get(0).getLocationId().equals(location1.getLocationId()));
        assertTrue(locations.get(1).getLocationId().equals(location2.getLocationId()));
        
        assertTrue(locations.get(0).getCity().equals(location1.getCity()));
        assertTrue(locations.get(1).getCity().equals(location2.getCity()));
        
        assertTrue(locations.get(0).getLongitude().equals(location1.getLongitude()));
        assertTrue(locations.get(1).getLongitude().equals(location2.getLongitude()));
        
        assertTrue(locations.get(0).getLattitude().equals(location1.getLattitude()));
        assertTrue(locations.get(1).getLattitude().equals(location2.getLattitude()));
        
    }
    
    @Test
    public void updateMustChangeLocation() {
        
        Session session = sf.getCurrentSession();
        
        LocationForm form = new LocationForm("Changed", 1.0, 1.0);
        
        locationDAO.update(location1.getLocationId(), form);
        
        Location newLocation = (Location) session.get(Location.class, location1.getLocationId());
        
        assertTrue("New Location must not be null", newLocation != null);
        assertTrue(newLocation.getLocationId().equals(location1.getLocationId()));
        assertTrue(newLocation.getCity().equals("Changed"));
        assertTrue(newLocation.getLattitude().equals(1.0));
        assertTrue(newLocation.getLongitude().equals(1.0));
        
    }
    
    @Test
    public void deleteMustDeleteLocation() {
        
        Session session = sf.getCurrentSession();
        
        String query = "from Location location where location.locationId = :locationId";
        Location newLocation = (Location) session.createQuery(query)
                .setParameter("locationId", location1.getLocationId()).uniqueResult();
        
        locationDAO.delete(newLocation);
        
        Location testLocation = session.get(Location.class, newLocation.getLocationId());
        
        assertTrue("Test location must be null", testLocation == null);
    }
    
    @Test
    public void deleteByIdMustDeleteLocation() {
        
        Session session = sf.getCurrentSession();
        
        String query = "from Location location where location.locationId = :locationId";
        Location newLocation = (Location) session.createQuery(query)
                .setParameter("locationId", location1.getLocationId()).uniqueResult();
        
        long locationId = newLocation.getLocationId();
        locationDAO.deleteById(locationId);
        
        Location testLocation = (Location) session.get(Location.class, locationId);
        
        assertTrue("Test location must be null", testLocation == null);
    }
}
