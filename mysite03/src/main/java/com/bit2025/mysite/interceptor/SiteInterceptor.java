package com.bit2025.mysite.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.LocaleResolver;

import com.bit2025.mysite.service.SiteService;
import com.bit2025.mysite.vo.SiteVo;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SiteInterceptor implements HandlerInterceptor {
	private ServletContext servletContext;
	private SiteService siteService;
	private LocaleResolver localeResolver;
	
	public SiteInterceptor(ServletContext servletContext, SiteService siteService,LocaleResolver localeResolver) {
		this.servletContext = servletContext;
		this.siteService = siteService;
		this.localeResolver = localeResolver;
	}
	
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