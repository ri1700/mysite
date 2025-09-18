package com.bit2025.mysite.initializer;

import java.util.ResourceBundle;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.bit2025.mysite.config.AppConfig;
import com.bit2025.mysite.config.WebConfig;

import jakarta.servlet.MultipartConfigElement;
import jakarta.servlet.ServletRegistration.Dynamic;

public class MySiteWebApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] {AppConfig.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] {WebConfig.class};
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] {"/"};
	}

	@Override
	protected void customizeRegistration(Dynamic registration) {
		ResourceBundle bundle = ResourceBundle.getBundle("com.bit2025.mysite.config.web.fileupload");
		long maxFileSize = Long.parseLong(bundle.getString("fileupload.maxFileSize"));
		long maxRequestSize = Long.parseLong(bundle.getString("fileupload.maxRequestSize"));
		int fileSizeThreshold = Integer.parseInt(bundle.getString("fileupload.fileSizeThreshold"));
		
		MultipartConfigElement multipartConfig = new MultipartConfigElement(null, maxFileSize, maxRequestSize, fileSizeThreshold);
		registration.setMultipartConfig(multipartConfig);
	}
	
}
