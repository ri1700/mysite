package com.bit2025.mysite.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.bit2025.mysite.service.FileuploadService;
import com.bit2025.mysite.service.SiteService;
import com.bit2025.mysite.vo.SiteVo;

import jakarta.servlet.ServletContext;

@Controller
@RequestMapping("/admin")
public class AdminController {
	private SiteService siteService;
	private FileuploadService fileuploadService;
	private ServletContext servletContext;
	private ApplicationContext applicationContext;
	
	public AdminController(SiteService siteService, FileuploadService fileuploadService, ServletContext servletContext, ApplicationContext applicationContext) {
		this.siteService = siteService;
		this.fileuploadService = fileuploadService;
		this.servletContext = servletContext;
		this.applicationContext = applicationContext;
	}
	
	@RequestMapping({"", "/"})
	public String main() {
		return "admin/main";
	}

	@RequestMapping("/main/update")
	public String mainUpdate(SiteVo vo, @RequestParam("file") MultipartFile multipartFile) {
		String profileURL = fileuploadService.restore(multipartFile);
		if(profileURL != null) {
			vo.setProfileURL(profileURL);
		}
		
		siteService.updateSite(vo);
		
		// update SiteVo bean in sevlet context 
		servletContext.setAttribute("siteVo", vo);

		// update SiteVo bean in application context 
		SiteVo site = applicationContext.getBean(SiteVo.class);
		BeanUtils.copyProperties(vo, site);
		
		return "redirect:/admin";
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
