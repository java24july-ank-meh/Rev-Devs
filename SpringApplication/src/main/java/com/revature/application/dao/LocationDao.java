package com.revature.application.dao;

import java.util.Set;

import com.revature.application.dao.beans.*;

public interface LocationDao {
	public boolean create(Location loc);
	public Company read(long loc_id);
	public Set<Location> readAll();
	public boolean update(Location loc);
	public boolean delete(Location loc);
}
