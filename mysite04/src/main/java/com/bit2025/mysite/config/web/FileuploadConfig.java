package com.bit2025.mysite.config.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class FileuploadConfig implements WebMvcConfigurer {

	// mvc url-resource mapping
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
	
		registry
			.addResourceHandler("/upload-images/**")
			.addResourceLocations("file:/mysite-uploads/");
	}
	
	@Bean
	public MultipartResolver multipartResolver() {
		
		return new StandardServletMultipartResolver();
	}
	
}