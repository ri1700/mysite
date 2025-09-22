package com.bit2025.mysite.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.bit2025.mysite.config.web.FileuploadConfig;
import com.bit2025.mysite.config.web.LocaleConfig;
import com.bit2025.mysite.config.web.MvcConfig;
import com.bit2025.mysite.event.ApplicationContextEventListener;
import com.bit2025.mysite.interceptor.SiteInterceptor;

@Configuration
@EnableAspectJAutoProxy
@EnableWebMvc
@ComponentScan(basePackages={"com.bit2025.mysite.controller", "com.bit2025.mysite.exception"})
@Import({LocaleConfig.class, FileuploadConfig.class, MvcConfig.class})
public class WebConfig implements WebMvcConfigurer {
	
	// Site Interceptor
	@Bean
	public SiteInterceptor siteInterceptor() {
		return new SiteInterceptor();
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry
			.addInterceptor(siteInterceptor())
			.addPathPatterns("/**")
			.excludePathPatterns("/assets/**");
	}

	// Application Context Event Listener
	@Bean
	public ApplicationContextEventListener applicationContextEventListener(ApplicationContext applicationContext) {
		return new ApplicationContextEventListener(applicationContext);
	}
}