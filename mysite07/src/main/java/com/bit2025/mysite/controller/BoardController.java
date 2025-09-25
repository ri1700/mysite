package com.bit2025.mysite.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.bit2025.mysite.service.BoardService;
import com.bit2025.mysite.vo.BoardVo;
import com.bit2025.mysite.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController {
	@Autowired
	private BoardService boardService;

	@RequestMapping("")
	public String index(
		@RequestParam(value="p", required=true, defaultValue="1") Integer page,
		@RequestParam(value="kwd", required=true, defaultValue="") String keyword,
		Model model) {
		
		Map<String, Object> map = boardService.getContentsList(page, keyword);

		// model.addAllAttributes(map);
		// for(String key : map.keySet()) {
		//	model.addAttribute(key, map.get(key));
		// }
		
		model.addAttribute("map", map);
		model.addAttribute("keyword", keyword);
		
		return "board/index";
	}
	
	@RequestMapping("/view/{id}")
	public String view(@PathVariable("id") Long id, Model model) {
		BoardVo boardVo = boardService.getContents(id);
		model.addAttribute("boardVo", boardVo);
		return "board/view";
	}
	
	@RequestMapping("/delete/{id}")
	public String delete(
		Authentication authentication, 
		@PathVariable("id") Long boardId,
		@RequestParam(value="p", required=true, defaultValue="1") Integer page,
		@RequestParam(value="kwd", required=true, defaultValue="") String keyword) {
		boardService.deleteContents(boardId, ((UserVo)authentication.getPrincipal()).getId());
		return "redirect:/board?p=" + page + "&kwd=" + keyword;
	}
	
	@RequestMapping(value="/modify/{id}", method=RequestMethod.GET)	
	public String modify(Authentication authentication, @PathVariable("id") Long id, Model model) {
		BoardVo boardVo = boardService.getContents(id, ((UserVo)authentication.getPrincipal()).getId());
		model.addAttribute("boardVo", boardVo);
		return "board/modify";
	}

	@RequestMapping(value="/modify", method=RequestMethod.POST)	
	public String modify(
		Authentication authentication, 
		BoardVo boardVo,
		@RequestParam(value="p", required=true, defaultValue="1") Integer page,
		@RequestParam(value="kwd", required=true, defaultValue="") String keyword) {
		boardVo.setUserId(((UserVo)authentication.getPrincipal()).getId());
		boardService.modifyContents(boardVo);
		return "redirect:/board/view/" + boardVo.getId() + 
				"?p=" + page + 
				"&kwd=" + keyword;
	}

	@RequestMapping(value="/write", method=RequestMethod.GET)	
	public String write() {
		return "board/write";
	}

	@RequestMapping(value="/write", method=RequestMethod.POST)	
	public String write(
		Authentication authentication,
		@ModelAttribute BoardVo boardVo,
		@RequestParam(value="p", required=true, defaultValue="1") Integer page,
		@RequestParam(value="kwd", required=true, defaultValue="") String keyword) {
		boardVo.setUserId(((UserVo)authentication.getPrincipal()).getId());
		boardService.addContents(boardVo);
		return	"redirect:/board?p=" + page + "&kwd=" + keyword;
	}

	@RequestMapping(value="/reply/{id}")	
	public String reply(@PathVariable("id") Long id, Model model) {
		BoardVo boardVo = boardService.getContents(id);
		boardVo.setOrderNo(boardVo.getOrderNo() + 1);
		boardVo.setDepth(boardVo.getDepth() + 1);
		
		model.addAttribute("boardVo", boardVo);
		
		return "board/reply";
	}	
}