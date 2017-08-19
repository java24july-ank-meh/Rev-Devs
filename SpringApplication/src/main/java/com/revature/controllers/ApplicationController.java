package com.revature.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApplicationController {

	@RequestMapping("/app")
	public String home() {
		return "index.html";
	}

}
