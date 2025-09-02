package com.bit2025.mysite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bit2025.mysite.service.GuestbookService;
import com.bit2025.mysite.vo.GuestbookVo;

@Controller
@RequestMapping("/guestbook")
public class GuestbookController {
	@Autowired
	private GuestbookService guestbookService;
	
	@RequestMapping("")
	public String list(Model model) {
		model.addAttribute("list", guestbookService.getMessageList());
		return "guestbook/list";
	}
	
	@RequestMapping("/add")
	public String add(GuestbookVo guestbookVo) {
		guestbookService.addMessage(guestbookVo);
		return "redirect:/guestbook";
	}
	
	@RequestMapping(value="/delete/{id}", method=RequestMethod.GET)
	public String delete(@PathVariable("id") Long id) {
		return "/guestbook/delete";
	}
	
	@RequestMapping(value="/delete/{id}", method=RequestMethod.POST)
	public String delete(@PathVariable("id") Long id, @RequestParam(value="password", required=true, defaultValue="") String password) {
		guestbookService.deleteMessage(id, password);
		return "redirect:/guestbook";
	}
	
}