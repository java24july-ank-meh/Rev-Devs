package com.revature.application.dao.implementations;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.management.Query;
import javax.persistence.criteria.CriteriaBuilder;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.jayway.jsonpath.Criteria;
import com.revature.application.dao.PostCommentDao;
import com.revature.application.dao.beans.Post;
import com.revature.application.dao.beans.PostComment;

public class PostCommentDaoImpl implements PostCommentDao{
	@Autowired
	SessionFactory sf;
	@Autowired
	CriteriaBuilder builder;

	@Transactional
	public boolean create(PostComment comment) {
		Session session = sf.getCurrentSession();
		session.save(comment);
		session.flush();
		session.close();
		return true;
	}

	@Transactional
	public List<Post> readAll() {		
		Session session = sf.getCurrentSession();
		
		List<Post> posts = session.createQuery("from Post post").list();

		return posts;
	}

	@Transactional
	public PostComment read(long comment_id) {
		Session session = sf.getCurrentSession();
		PostComment comment = session.get(PostComment.class, comment_id);
		session.flush();
		session.close();
		
		return comment;
	}

	@Transactional
	public boolean update(PostComment comment) {
		Session session = sf.getCurrentSession();
		session.saveOrUpdate(comment);
		session.flush();
		
		return true;
	}

	@Transactional
	public boolean delete(PostComment comment) {
		Session session = sf.getCurrentSession();
		session.delete(comment);
		session.flush();
		
		return true;
	}

	@Transactional
	public boolean deleteById(long comment_id) {
		Session session = sf.getCurrentSession();

		PostComment comment = new PostComment();
		comment.setCommentId(comment_id);
		session.delete(comment);
		
		session.flush();
		
		return true;
	}
	
}
