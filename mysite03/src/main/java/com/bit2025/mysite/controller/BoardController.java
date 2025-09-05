package com.bit2025.mysite.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
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
import jakarta.servlet.http.HttpSession;

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
		HttpSession session, 
		@PathVariable("id") Long boardId,
		@RequestParam(value="p", required=true, defaultValue="1") Integer page,
		@RequestParam(value="kwd", required=true, defaultValue="") String keyword) {
		// 접근제어
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/";
		}
		///////////////////////////////////////////////////////////

		boardService.deleteContents(boardId, authUser.getId());
		return "redirect:/board?p=" + page + "&kwd=" + keyword;
	}
	
	@RequestMapping("/modify/{id}")	
	public String modify(HttpSession session, @PathVariable("id") Long id, Model model) {
		// 접근제어
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/";
		}
		///////////////////////////////////////////////////////////
		
		BoardVo boardVo = boardService.getContents(id, authUser.getId());
		model.addAttribute("boardVo", boardVo);
		return "board/modify";
	}

	@RequestMapping(value="/modify", method=RequestMethod.POST)	
	public String modify(
		HttpSession session, 
		BoardVo boardVo,
		@RequestParam(value="p", required=true, defaultValue="1") Integer page,
		@RequestParam(value="kwd", required=true, defaultValue="") String keyword) {
		// 접근제어
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/";
		}
		///////////////////////////////////////////////////////////
		
		boardVo.setUserId(authUser.getId());
		boardService.modifyContents(boardVo);
		return "redirect:/board/view/" + boardVo.getId() + 
				"?p=" + page + 
				"&kwd=" + keyword;
	}

	@RequestMapping(value="/write", method=RequestMethod.GET)	
	public String write(HttpSession session) {
		// 접근제어
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/";
		}
		///////////////////////////////////////////////////////////
		
		return "board/write";
	}

	@RequestMapping(value="/write", method=RequestMethod.POST)	
	public String write(
		HttpSession session,
		@ModelAttribute BoardVo boardVo,
		@RequestParam(value="p", required=true, defaultValue="1") Integer page,
		@RequestParam(value="kwd", required=true, defaultValue="") String keyword) {
		// 접근제어
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/";
		}
		///////////////////////////////////////////////////////////
		
		boardVo.setUserId(authUser.getId());
		boardService.addContents(boardVo);
		return	"redirect:/board?p=" + page + "&kwd=" + keyword;
	}

	@RequestMapping(value="/reply/{id}")	
	public String reply(HttpSession session, @PathVariable("id") Long id, Model model) {
		// 접근제어
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/";
		}
		///////////////////////////////////////////////////////////
			
		BoardVo boardVo = boardService.getContents(id);
		boardVo.setOrderNo(boardVo.getOrderNo() + 1);
		boardVo.setDepth(boardVo.getDepth() + 1);
		
		model.addAttribute("boardVo", boardVo);
		
		return "board/reply";
	}	
}