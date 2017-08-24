package com.revature.application.dao.beans;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.revature.application.dao.*;
import com.revature.application.dao.beans.*;
import com.revature.application.dao.implementations.*;

public class Test2 {
	public static void main(String[] args) throws Exception {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("appContext.xml");
		LocationDao service = (LocationDao)ctx.getBean("locationService");
		//service.create(new Location("New York",99,55));
		System.out.println(service.readAll());
		
		System.out.println("done");
	}

}
