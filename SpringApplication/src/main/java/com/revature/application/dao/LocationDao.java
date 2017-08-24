package com.revature.application.dao;

import java.util.List;

import com.revature.application.dao.beans.*;

public interface LocationDao {
	public boolean create(Location loc);
	public Location read(long loc_id);
	public List<Location> readAll();
	public boolean update(Location loc);
	public boolean delete(Location loc);
	public boolean deleteById(long loc_id);
}
