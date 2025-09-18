package com.bit2025.mysite.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.LocaleResolver;

import com.bit2025.mysite.service.SiteService;
import com.bit2025.mysite.vo.SiteVo;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SiteInterceptor implements HandlerInterceptor {
	@Autowired
	private ServletContext servletContext;
	
	@Autowired
	private SiteService siteService;
	
	@Autowired
	private LocaleResolver localeResolver;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// Locale (Cookie: lang)
		String lang = localeResolver.resolveLocale(request).getLanguage();
		request.setAttribute("lang", lang);
		
		// Global SiteVo Bean
		SiteVo siteVo = (SiteVo)servletContext.getAttribute("siteVo");
		if(siteVo == null) {
			siteVo = siteService.getSite();
			servletContext.setAttribute("siteVo", siteVo);
		}
		
		return true;
	}

}
