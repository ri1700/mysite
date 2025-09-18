package com.bit2025.web;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpFilter;


public class MyFilter02 extends HttpFilter implements Filter {
	private static final long serialVersionUID = 1L;

	public void init(FilterConfig fConfig) throws ServletException {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		/* request */
		System.out.println("MyFilter02.doFilter() called: request processing");
		request.setAttribute("test02", "hello again");
		
		chain.doFilter(request, response);
		
		/* response */
		System.out.println("MyFilter02.doFilter() called: response processing");
	}

	public void destroy() {
	}
}