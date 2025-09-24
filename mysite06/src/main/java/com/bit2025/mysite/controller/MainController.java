package com.bit2025.mysite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.bit2025.mysite.vo.UserVo;

@Controller
public class MainController {
	@RequestMapping({"/", "/main"})
	public String index() {
		return "main/index";
	}
	
	// Examples for Message Converter
	@ResponseBody
	@RequestMapping("/msg01")
	public String message01() {
		return "hello world";
	}
	
	@ResponseBody
	@RequestMapping("/msg02")
	public String message02() {
		return "안녕 세상";
	}

	@ResponseBody
	@RequestMapping("/msg03")
	public UserVo message03() {
		UserVo vo = new UserVo();
		vo.setId(10L);
		vo.setName("둘리");
		vo.setEmail("dooly@gmail.com");
		
		return vo;
	}
	
}
