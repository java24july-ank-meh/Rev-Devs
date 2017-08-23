package com.revature.application.dao;

import java.util.List;

import com.revature.application.dao.beans.*;

public interface PostTypeDao {
	public boolean create(PostType type);
	public PostType read(long type_id);
	public List<PostType> readAll();
	public boolean update(PostType type);
	public boolean delete(PostType type);
	public boolean deleteById(long type_id);
}
