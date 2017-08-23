package com.revature.application.dao.implementations;

import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.revature.application.dao.PostCommentDao;
import com.revature.application.dao.PostDao;
import com.revature.application.dao.beans.Post;
import com.revature.application.dao.beans.PostComment;

public class PostDaoImpl implements PostDao{
	@Autowired
	SessionFactory sf;

	@Override
	public boolean create(Post post) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Post> readAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Post read(long post_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean update(Post post) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Post post) {
		Session session = sf.getCurrentSession();
		session.delete(comment);
		session.flush();
		
		return true;
	}

	@Override
	public boolean deleteById(long post_id) {
		Session session = sf.getCurrentSession();

		Post post = new Post();
		post.setPostId(post_id);
		session.delete(post);
		
		session.flush();
		session.close();
		
		return true;
	}
	
}
