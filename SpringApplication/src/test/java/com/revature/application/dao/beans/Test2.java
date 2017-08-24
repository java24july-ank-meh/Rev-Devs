package com.revature.application.dao.beans;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
//import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.revature.application.RevatureSocialNetworkApplication;
import com.revature.application.dao.*;
import com.revature.application.dao.implementations.*;

//@RunWith(SpringRunner.class)
@SpringBootTest(classes = RevatureSocialNetworkApplication.class)
//@WebAppConfiguration
public class Test2 {
	
	/*
	 * Only use the @Autowire annotations
	 * Make sure to look at the tests for the restControllers
	 * If we set up the annotations at the top correctly, spring
	 * will automatically inject the locationDAO 
	 * 
	 * need to convert to JUnit and use Mockito
	 */
	
	@Autowired
	private LocationDaoImpl locationDAO;
	
	public static void main(String[] args) throws Exception {
	
		
		//System.out.println(locationDAO.readAll());
		
		System.out.println("done");
	}

}
