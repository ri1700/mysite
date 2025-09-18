package com.bit2025.mysite.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bit2025.mysite.dto.JsonResult;
import com.bit2025.mysite.service.UserService;
import com.bit2025.mysite.vo.UserVo;

@RestController("ApiUserController")
@RequestMapping("/api/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	@GetMapping("/checkemail")
	public JsonResult checkEmail(@RequestParam(value="email", required=true, defaultValue="")String email) {
		UserVo vo = userService.getUser(email);
		return JsonResult.success(vo != null);
	}
}
