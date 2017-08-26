package com.revature.application.dao;

import java.util.List;

import com.revature.application.dao.beans.Location;
import com.revature.application.dao.beans.forms.LocationForm;

public interface LocationDao {
	public boolean create(LocationForm locationForm);
	public Location read(long loc_id);
	public List<Location> readAll();
	public boolean update(long loc_id, LocationForm locationForm);
	public boolean delete(Location loc);
	public boolean deleteById(long loc_id);
}
