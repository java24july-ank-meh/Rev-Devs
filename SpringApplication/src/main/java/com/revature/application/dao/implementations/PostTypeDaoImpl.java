package com.revature.application.dao.implementations;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revature.application.dao.PostTypeDao;
import com.revature.application.dao.beans.PostType;

@Service
public class PostTypeDaoImpl implements PostTypeDao{

	@Autowired
	SessionFactory sf;

	@Override
	@Transactional
	public boolean create(PostType type) {
		
		Session session = sf.getCurrentSession();
		
		session.save(type);
		session.flush();
		 
		return true;
	}

	@Override
	@Transactional
	public PostType read(long type_id) {
		
		Session session = sf.getCurrentSession();
		
		PostType type = session.get(PostType.class, type_id);
		session.flush();
		 
		return type;
	}

	@Override
	@Transactional
	public List<PostType> readAll() {
		
		Session session = sf.getCurrentSession();
		
		List<PostType> types = session.createQuery("from PostType type").list();
		session.flush();
		
		return types;
	}

	@Override
	@Transactional
	public boolean update(PostType type) {
		
		Session session = sf.getCurrentSession();
		
		session.update(type);
		session.flush();

		return true;
	}

	@Override
	@Transactional
	public boolean delete(PostType type) {
		
		Session session = sf.getCurrentSession();
		
		session.delete(type);
		session.flush();

		return true;
	}

	@Override
	@Transactional
	public boolean deleteById(long type_id) {
		
		Session session = sf.getCurrentSession();
		
		PostType type = new PostType();
		type.setTypeId(type_id);
		
		session.delete(type);
		session.flush();

		return true;
	}
	
}
