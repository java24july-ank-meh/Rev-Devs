package com.revature.application.dao;

import java.util.List;

import com.revature.application.dao.beans.PostComment;
import com.revature.application.dao.beans.forms.PostCommentForm;

public interface PostCommentDao {
	public boolean create(PostCommentForm commentForm);
	public List<PostComment> readAll();
	public PostComment read(long comment_id);
	public boolean update(long comment_id, PostCommentForm commentForm);
	public boolean delete(PostComment comment);
	public boolean deleteById(long comment_id);
}
