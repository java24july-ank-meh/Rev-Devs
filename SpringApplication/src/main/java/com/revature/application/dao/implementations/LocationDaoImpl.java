package com.revature.application.dao.implementations;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revature.application.dao.LocationDao;
import com.revature.application.dao.beans.Location;
import com.revature.application.dao.beans.forms.LocationForm;

@Service
public class LocationDaoImpl implements LocationDao {
	
	@Autowired
	SessionFactory sf;
	
	@Override
	@Transactional
	public boolean create(LocationForm locationForm) {
		
		Session session = sf.getCurrentSession();
		
		Location location = new Location(locationForm.getCity(), locationForm.getLongitude(), locationForm.getLattitude());
		
		session.save(location);
		session.flush();
		 
		return true;
	}

	@Override
	@Transactional
	public Location read(long loc_id) {
		
		Session session = sf.getCurrentSession();
		Location loc = session.get(Location.class, loc_id);
		session.flush();

		return loc;
	}

	@Override
	@Transactional
	public List<Location> readAll() {
		
		Session session = sf.getCurrentSession();
		
		List<Location> locations = session.createQuery("from Location location").list();
		session.flush();
		
		return locations;
	}

	@Override
	@Transactional
	public boolean update(long loc_id, LocationForm locationForm) {
		
		Session session = sf.getCurrentSession();
		
//        Location location = new Location(locationForm.getCity(), locationForm.getLongitude(), locationForm.getLattitude());
//		location.setLocationId(loc_id);
		
		Location location = session.get(Location.class, loc_id);
		location.setCity(locationForm.getCity());
		location.setLattitude(locationForm.getLattitude());
		location.setLongitude(locationForm.getLongitude());
           
		session.update(location);
		session.flush();
			
		return true;
	}

	@Override
	@Transactional
	public boolean delete(Location loc) {
		
		Session session = sf.getCurrentSession();

		session.delete(loc);
		session.flush();
		
		return true;
	}

	@Override
	@Transactional
	public boolean deleteById(long loc_id) {
		
		Session session = sf.getCurrentSession();

//		Location loc = new Location();
//		loc.setLocationId(loc_id);
		Location loc = session.get(Location.class, loc_id);
		
		session.delete(loc);
		/*org.hibernate.NonUniqueObjectException: 
		 * A different object with the same identifier value was already associated with the session 
		 * : [com.revature.application.dao.beans.Location#4652]*/
		session.flush();
		
		return true;
	}
	
	@Transactional
	public boolean deleteById2(long loc_id) {
		
		Session session = sf.getCurrentSession();
		
		Location loc = null;
		
		Query query = session.createQuery("delete Location where locationId = :ID");
		query.setParameter("ID", loc_id);
		
		int result = query.executeUpdate();
		System.out.println("result: " + result);
//		session.delete(loc);
		/*org.hibernate.NonUniqueObjectException: 
		 * A different object with the same identifier value was already associated with the session 
		 * : [com.revature.application.dao.beans.Location#4652]*/
		session.flush();
		
		return true;
	}

}
