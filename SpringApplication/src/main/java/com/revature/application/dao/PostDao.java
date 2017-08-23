package com.revature.application.dao;

import java.util.List;

import com.revature.application.dao.beans.*;

public interface PostDao {
	public boolean create(Post post);
	public List<Post> readAll();
	public Post read(long post_id);
	public boolean update(Post post);
	public boolean delete(Post post);
	public boolean deleteById(long post_id);
}
