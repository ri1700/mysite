package com.bit2025.mysite.service;

import org.springframework.stereotype.Service;

import com.bit2025.mysite.repository.SiteRepository;
import com.bit2025.mysite.vo.SiteVo;

@Service
public class SiteService {
	private SiteRepository siteRepository;
	
	public SiteService(SiteRepository siteRepository) {
		this.siteRepository = siteRepository;
	}
	
	public SiteVo getSite() {
		return siteRepository.findById(1L);
	}
	
	public void updateSite(SiteVo vo) {
		vo.setId(1L);
		siteRepository.update(vo);
	}
}
