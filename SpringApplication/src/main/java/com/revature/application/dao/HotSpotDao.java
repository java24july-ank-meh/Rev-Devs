package com.revature.application.dao;

import java.util.List;

import com.revature.application.dao.beans.HotSpot;
import com.revature.application.dao.beans.forms.HotSpotForm;

public interface HotSpotDao {
	public List<HotSpot> readAllHotSpotsByLocationId(long locationId);
	public boolean createHotSpot(HotSpotForm hotSpotForm);
}
