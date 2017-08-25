package com.revature.application.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ApplicationController {

	@RequestMapping("/")
	public String loginPage(Model model) {
		return "login.html";
	}
	
	@RequestMapping("/home")
	public String homePage(Model model) {
		return "index.html";
	}
	
	@RequestMapping("/map")
	public String mapPage(Model model) {
		return "map.html";
	}

}
