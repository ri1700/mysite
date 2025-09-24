package com.bit2025.mysite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import jakarta.validation.Valid;
import com.bit2025.mysite.service.UserService;
import com.bit2025.mysite.vo.UserVo;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/join", method=RequestMethod.GET)
	public String join(@ModelAttribute UserVo userVo) {
		return "user/join";
	}
	
	@RequestMapping(value="/join", method=RequestMethod.POST)
	public String join(@ModelAttribute @Valid UserVo userVo, BindingResult result, Model model) {
		if(result.hasErrors()) {
			//System.out.println(result.getModel());
			model.addAllAttributes(result.getModel());
			return "user/join";
		}
		
		userService.join(userVo);
		return "redirect:/user/joinsuccess";
	}

	@RequestMapping("/joinsuccess")
	public String joinsuccess() {
		return "user/joinsuccess";
	}

	@RequestMapping(value="/login")
	public String login() {
		return "user/login";
	}

	@RequestMapping(value="/update", method=RequestMethod.GET)
	public String update(Authentication authentication, Model model) {
		// 1. HttpSession을 사용하는 방법
		// SecurityContext securityContext = (SecurityContext)session.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
		// Authentication authentication = securityContext.getAuthentication();
		// UserVo authUser = (UserVo)authentication.getPrincipal();
		
		// 2. SecurityContextHolder(Spring Security ThreadLocal Helper)
		// SecurityContext securityContext = SecurityContextHolder.getContext();
		// Authentication authentication = securityContext.getAuthentication();
		// UserVo authUser = (UserVo)authentication.getPrincipal();		
		
		Long id = ((UserVo)authentication.getPrincipal()).getId();
		UserVo userVo = userService.getUser(id);
		
		model.addAttribute("userVo", userVo);
		return "user/update";
	}

	@RequestMapping(value="/update", method=RequestMethod.POST)
	public String update(Authentication authentication, UserVo userVo) {
		UserVo authUser = (UserVo)authentication.getPrincipal();
		
		userVo.setId(authUser.getId());
		userService.updateUser(userVo);
		authUser.setName(userVo.getName());
		
		return "redirect:/user/update";
	}
}
