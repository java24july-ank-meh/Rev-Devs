package com.revature.application.dao.beans;

import java.util.Date;

import org.hibernate.Session;

import com.revature.application.dao.beans.Employee;
import com.revature.application.dao.beans.HibUtil;
import com.revature.application.dao.beans.Location;
import com.revature.application.dao.beans.Post;
import com.revature.application.dao.beans.PostComment;
import com.revature.application.dao.beans.PostType;

public class Test {

	public static void main(String[] args) throws Exception {
		Session session = HibUtil.getSessionFactory().openSession();
		session.beginTransaction();

		// Location loc = new Location(2,"Los Angeles",100,122);
		// session.save(loc);

		// PostType type = new PostType("housing");
		// session.save(type);

		// Post post = new Post(loc,employee,type,new Date(),"blah blah blah");
		// session.save(post);

		// PostComment comment = new PostComment(employee,post,new Date(),"comment blah
		// blah blah");
		// session.save(comment);

		// Company company = new Company(loc,"Revature");
		// session.save(company);

		//Employee employee = new Employee(loc,company,"aaa","pass123","aaa@gmail.com","alex","jones");
		//session.save(employee);

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

		session.getTransaction().commit();
		HibUtil.Shutdown();
	}

}
