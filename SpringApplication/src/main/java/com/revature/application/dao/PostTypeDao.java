package com.revature.application.dao;

import java.util.List;

import com.revature.application.dao.beans.PostType;
import com.revature.application.dao.beans.forms.PostTypeForm;

public interface PostTypeDao {
	public boolean create(PostTypeForm typeForm);
	public PostType read(long type_id);
	public List<PostType> readAll();
	public boolean update(long type_id, PostTypeForm typeForm);
	public boolean delete(PostType type);
	public boolean deleteById(long type_id);
}
