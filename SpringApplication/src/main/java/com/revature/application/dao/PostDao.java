package com.revature.application.dao;

import java.util.List;

import com.revature.application.dao.beans.Post;
import com.revature.application.dao.beans.forms.PostForm;

public interface PostDao {
	public boolean create(PostForm postForm);
	public List<Post> readAll();
	public Post read(long post_id);
	public boolean update(long post_id, PostForm postForm);
	public boolean delete(Post post);
	public boolean deleteById(long post_id);
}
