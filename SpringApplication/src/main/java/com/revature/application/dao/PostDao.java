package com.revature.application.dao;

import com.revature.application.dao.beans.*;

public interface PostDao {
	public boolean create(Post post);
	public Post read(long post_id);
	public boolean update(Post post);
	public boolean delete(Post post);
}
