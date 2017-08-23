package com.revature.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:appContext.xml")
public class RevatureSocialNetworkApplication {

	public static void main(String[] args) {
		SpringApplication.run(RevatureSocialNetworkApplication.class, args);
	}
}
