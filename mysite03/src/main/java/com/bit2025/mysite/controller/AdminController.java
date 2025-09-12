package com.bit2025.mysite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bit2025.mysite.security.Auth;

@Controller
@RequestMapping("/admin")
@Auth(role="ADMIN")
public class AdminController {
	
	@RequestMapping({"", "/"})
	public String main() {
		return "admin/main";
	}

	public String mainUpdate() {
		return "";
	}
	
	@RequestMapping("/guestbook")
	public String guestbook() {
		return "admin/guestbook";
	}
	
	@RequestMapping("/board")
	public String board() {
		return "admin/board";
	}

	@RequestMapping("/user")
	public String user() {
		return "admin/user";
	}
	
}