package com.revature.application.dao.implementations;

import java.util.Set;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.revature.application.dao.PostCommentDao;
import com.revature.application.dao.PostTypeDao;
import com.revature.application.dao.beans.PostType;

public class PostTypeDaoImpl implements PostTypeDao{

	@Autowired
	SessionFactory sf;

	@Override
	public boolean create(PostType type) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public PostType read(long type_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<PostType> readAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean update(PostType type) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(PostType type) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteById(long type_id) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
