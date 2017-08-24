package com.revature.application.dao.beans;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.revature.application.dao.DataService;

public class Test2 {
	public static void main(String[] args) throws Exception {
		ApplicationContext ctx = 
				new ClassPathXmlApplicationContext("appContext.xml");
		DataService service = 
				(DataService)ctx.getBean("service");
		service.insert();
		System.out.println("done");
	}

}
