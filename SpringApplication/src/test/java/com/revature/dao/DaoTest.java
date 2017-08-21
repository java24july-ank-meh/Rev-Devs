package com.revature.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.revature.dao.Location;

public class DaoTest {

	public static void main(String[] args) {
		try {
			SessionFactory sf = new Configuration().configure().buildSessionFactory();
			Session session = sf.openSession();
			Transaction tx = session.beginTransaction();
			
			Location loc = new Location(2,"Los Angeles",100,122);
			session.save(loc);
			
			Employee user = new Employee(null,null,"user123","password","user@email.com","fname","lname");
			session.save(user);
			
			session.flush();
			tx.commit();
			session.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
