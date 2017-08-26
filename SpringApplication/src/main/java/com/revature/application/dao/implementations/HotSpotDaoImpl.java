package com.revature.application.dao.implementations;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revature.application.dao.HotSpotDao;
import com.revature.application.dao.beans.HotSpot;
import com.revature.application.dao.beans.Location;
import com.revature.application.dao.beans.forms.HotSpotForm;

@Service
public class HotSpotDaoImpl implements HotSpotDao {
    
    @Autowired
    SessionFactory sf;
    
    @Override
    @Transactional
    public List<HotSpot> readAllHotSpotsByLocationId(long locationId) {
        
        Session session = sf.getCurrentSession();
        
        String query = "from HotSpot hotSpot where hotSpot.locationId = :location";
        List<HotSpot> hotSpots = session.createQuery(query).setParameter("location", locationId)
                .list();
        session.flush();
        
        return hotSpots;
    }
    
    @Override
    @Transactional
    public boolean createHotSpot(HotSpotForm hotSpotForm) {
        
        Session session = sf.getCurrentSession();
        
        Location location = new Location();
        location.setLocationId(hotSpotForm.getLocationId());
        
        HotSpot hotSpot = new HotSpot(hotSpotForm.getLongitude(), hotSpotForm.getLattitude(),
                location);
        
        session.save(hotSpot);
        session.flush();
        
        return true;
    }
    
}
