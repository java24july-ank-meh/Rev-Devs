package com.revature.application.dao.implementations;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revature.application.dao.PostDao;
import com.revature.application.dao.beans.Post;

@Service
public class PostDaoImpl implements PostDao{
	@Autowired
	SessionFactory sf;

	@Transactional
	public boolean create(Post post) {
		Session session = sf.getCurrentSession();
		session.save(post);
		session.flush();
		 
		return true;
	}

	@Transactional
	public List<Post> readAll()  {
		Session session = sf.getCurrentSession();
		
		List<Post> posts = session.createQuery("from Post post").list();
		session.flush();
		
		return posts;
	}

	@Transactional
	public Post read(long post_id) {
		Session session = sf.getCurrentSession();
		Post post = session.get(Post.class, post_id);
		session.flush();

		return post;
	}

	@Transactional
	public boolean update(Post post) {
		Session session = sf.getCurrentSession();
		session.saveOrUpdate(post);
		session.flush();
			
		return true;
	}

	@Transactional
	public boolean delete(Post post) {
		Session session = sf.getCurrentSession();
		session.delete(post);
		session.flush();
		
		return true;
	}

	@Transactional
	public boolean deleteById(long post_id) {
		Session session = sf.getCurrentSession();

		Post post = new Post();
		post.setPostId(post_id);
		session.delete(post);
		
		session.flush();
		
		return true;
	}
	
}
