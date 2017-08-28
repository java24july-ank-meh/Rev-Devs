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
import com.revature.application.dao.HotSpotDao;
import com.revature.application.dao.beans.HotSpot;
import com.revature.application.dao.beans.Location;
import com.revature.application.dao.beans.forms.HotSpotForm;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RevatureSocialNetworkApplication.class)
@Transactional
public class HotSpotDaoImplTest {
    
    @Autowired
    private SessionFactory sf;
    
    @Autowired
    private HotSpotDao hotSpotDAO;
    
    // Objects we want to attach to hotSpots
    Location location = null;
    
    // Objects we want to persist in db
    HotSpot hotSpot1 = null;
    HotSpot hotSpot2 = null;
    HotSpot hotSpot3 = null;
    
    @Before
    public void setup() {
        
        Session session = sf.getCurrentSession();
        
        location = new Location("LA", 123.12, 123.12);
        
        session.saveOrUpdate(location);
        
        hotSpot1 = new HotSpot(123.12, 123.12, location);
        hotSpot2 = new HotSpot(11.11, 11.11, location);
        hotSpot3 = new HotSpot(1.0, 1.0, location);
        
        session.saveOrUpdate(hotSpot1);
        session.saveOrUpdate(hotSpot2);
        // The last hotSpot3 will be used to persist using our api
        
    }
    
    @Test
    public void locationMustBeInDB() {
        
        Session session = sf.getCurrentSession();
        
        Location newLocation = (Location) session.get(Location.class, location.getLocationId());
        
        assertTrue("Location object must be persisted to DB", newLocation != null);
    }
    
    @Test
    public void createMethodMustSaveToDb() {
        
        Session session = sf.getCurrentSession();
        
        HotSpotForm hotSpotForm = new HotSpotForm(hotSpot3.getLongitude(), hotSpot3.getLattitude(),
                hotSpot3.getLocation().getLocationId());
        
        hotSpotDAO.createHotSpot(hotSpotForm);
        
        String query = "from HotSpot hotSpot where hotSpot.longitude = :longitude and hotSpot.lattitude = :lattitude";
        HotSpot newHotSpot = (HotSpot) session.createQuery(query)
                .setParameter("longitude", hotSpot3.getLongitude())
                .setParameter("lattitude", hotSpot3.getLattitude()).uniqueResult();
        
        assertTrue("HotSpot object must be persisted to DB", newHotSpot != null);
        assertTrue(newHotSpot.getLongitude().equals(hotSpot3.getLongitude()));
        assertTrue(newHotSpot.getLattitude().equals(hotSpot3.getLattitude()));
    }
    
    @Test
    public void createMethodMissingParamMustNotSaveToDb() {
        
        Session session = sf.getCurrentSession();
        
        HotSpotForm hotSpotForm = new HotSpotForm(null, hotSpot3.getLattitude(),
                hotSpot3.getLocation().getLocationId());
        
        hotSpotDAO.createHotSpot(hotSpotForm);
        
        String query = "from HotSpot hotSpot where hotSpot.longitude = :longitude and hotSpot.lattitude = :lattitude";
        HotSpot newHotSpot = (HotSpot) session.createQuery(query)
                .setParameter("longitude", null)
                .setParameter("lattitude", hotSpot3.getLattitude()).uniqueResult();
        
        assertTrue("HotSpot object must not be persisted to DB", newHotSpot == null);
    }
    
    @Test
    public void readAllByIdGetsTheProperHotSpotObjects() {
        
        Session session = sf.getCurrentSession();
        
        List<HotSpot> hotSpots = hotSpotDAO.readAllHotSpotsByLocationId(location.getLocationId());
        
        assertTrue("Companies must not be null", hotSpots != null);
        
        hotSpots.sort((item1, item2) -> {
            if (item1.getHotSpotId() < item2.getHotSpotId()) {
                return -1;
            } else if (item1.getHotSpotId() > item2.getHotSpotId()) {
                return 1;
            } else {
                return 0;
            }
        });
        
        assertTrue(hotSpots.size() == 2);
        
        assertTrue(hotSpots.get(0).getHotSpotId().equals(hotSpot1.getHotSpotId()));
        assertTrue(hotSpots.get(1).getHotSpotId().equals(hotSpot2.getHotSpotId()));
        
        assertTrue(hotSpots.get(0).getLongitude().equals(hotSpot1.getLongitude()));
        assertTrue(hotSpots.get(1).getLongitude().equals(hotSpot2.getLongitude()));
        
        assertTrue(hotSpots.get(0).getLattitude().equals(hotSpot1.getLattitude()));
        assertTrue(hotSpots.get(1).getLattitude().equals(hotSpot2.getLattitude()));
    }
}
