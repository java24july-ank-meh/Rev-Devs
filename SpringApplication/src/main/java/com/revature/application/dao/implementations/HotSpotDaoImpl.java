package com.revature.application.dao.implementations;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revature.application.dao.HotSpotDao;
import com.revature.application.dao.beans.HotSpot;

@Service
public class HotSpotDaoImpl implements HotSpotDao {

	@Autowired
	SessionFactory sf;
	
	
	@Override
	@Transactional
	public List<HotSpot> readAllHotSpotsByLocationId(long locationId) {
		
		Session session = sf.getCurrentSession();
		
		String query = "from HotSpot hotSpot";
		List<HotSpot> hotSpots = session.createQuery(query).list();
		session.flush();
	
		return hotSpots;
	}

	@Override
	@Transactional
	public boolean createHotSpot(HotSpot hotSpot) {
		
		Session session = sf.getCurrentSession();
		
		session.save(hotSpot);
		session.flush();
		
		return true;
	}

}
