package com.revature.application.dao;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.revature.application.dao.Employee;
import com.revature.application.dao.Location;

public class DaoTest {

	public static void main(String[] args) {
		try {
			SessionFactory sf = new Configuration().configure().buildSessionFactory();
			Session session = sf.openSession();
			Transaction tx = session.beginTransaction();
			
			//Location loc = new Location(2,"Los Angeles",100,122);
			//session.save(loc);
			
			//PostType type = new PostType("housing");
			//session.save(type);
			
			//Post post = new Post(loc,employee,type,new Date(),"blah blah blah");
			//session.save(post);
			
			//PostComment comment = new PostComment(employee,post,new Date(),"comment blah blah blah");
			//session.save(comment);
			
			//Company company = new Company(loc,"Revature");
			//session.save(company);
			
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
			
			session.flush();
			tx.commit();
			session.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
