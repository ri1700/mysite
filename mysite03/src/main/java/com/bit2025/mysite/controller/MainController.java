package com.bit2025.mysite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
	
	@RequestMapping({"/","/main"})
	public String index() {
		
		return "main/index";	//DefaultServlet 제대로 알기
	}
}
