package com.revature.application.dao;

import java.util.List;

import com.revature.application.dao.beans.HotSpot;

public interface HotSpotDao {
	public List<HotSpot> readAllHotSpotsByLocationId(long locationId);
	public boolean createHotSpot(HotSpot hotSpot);
}
