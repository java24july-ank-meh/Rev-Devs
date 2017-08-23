package com.revature.application.dao;

import com.revature.application.dao.beans.*;

public interface PostCommentDao {
	public boolean create(PostComment comment);
	public PostComment read(long comment_id);
	public boolean update(PostComment comment);
	public boolean delete(PostComment comment);
	public boolean deleteById(long comment_id);
}
