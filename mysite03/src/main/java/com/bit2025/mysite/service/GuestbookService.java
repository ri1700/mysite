package com.bit2025.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bit2025.mysite.repository.GuestbookRepository;
import com.bit2025.mysite.vo.GuestbookVo;

@Service
public class GuestbookService {
	@Autowired
	private GuestbookRepository guestbookRepository;
	
	public List<GuestbookVo> getMessageList() {
		return guestbookRepository.findAll();
	}
	
	public void addMessage(GuestbookVo vo) {
		guestbookRepository.insert(vo);
	}

	public void deleteMessage(GuestbookVo vo) {
		guestbookRepository.deleteByIdAndPassword(vo.getId(), vo.getPassword());
	}

	public void deleteMessage(Long id, String password) {
		guestbookRepository.deleteByIdAndPassword(id, password);
	}
}