package com.revature.application.dao;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revature.application.dao.beans.Company;
import com.revature.application.dao.beans.Employee;
import com.revature.application.dao.beans.Location;
import com.revature.application.dao.beans.Post;
import com.revature.application.dao.beans.PostComment;
import com.revature.application.dao.beans.PostType;

@Service
public class DataService {

	@Autowired
	SessionFactory sf;
	
	public void setSf(SessionFactory sf) {
		this.sf = sf;
	}

	@Transactional
	public void insert() {
		Session session = sf.getCurrentSession();
		Location loc = new Location("New York",50,20);
		session.save(loc);
		PostType type = new PostType("food");
		session.save(type);
		
		/*Company company = new Company(loc,"Revature");
		session.save(company);
		Employee employee = new Employee(loc,company,"aaa","pass123","aaa@gmail.com","alex","jones");
		session.save(employee);
		Post post = new Post(loc,employee,type,new Date(),"blah blah blah");
		session.save(post);
		PostComment comment = new PostComment(employee,post,new Date(),"comment blah blah blah");
		session.save(comment);*/
		
		session.flush();
	}
	
	@Transactional
	public void print() {
		Session session = sf.getCurrentSession();
		PostType type = session.load(PostType.class, 20L);
		System.out.println(type);
		Location loc = session.load(Location.class, 15L);
		System.out.println(loc);
		Post post = session.load(Post.class, 22L);
		System.out.println(post);
		PostComment comment = session.load(PostComment.class, 24L);
		System.out.println(comment);
		Company company = session.load(Company.class, 25L);
		System.out.println(company);
		Employee employee = session.load(Employee.class, 26L);
		System.out.println(employee);
	}

}
