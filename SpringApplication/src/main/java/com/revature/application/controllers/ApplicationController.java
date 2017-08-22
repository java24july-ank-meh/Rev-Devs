package com.revature.application.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ApplicationController {

	@RequestMapping("/loginPage")
	public String loginPage(Model model) {
		return "login.html";
	}

}
