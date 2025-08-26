package com.bit2025.web;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

public class ContextLoadListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sce)  { 
    	ServletContext sc = sce.getServletContext();
    	String contextConfigLocation = sc.getInitParameter("contextConfigLocation");
    	
    	System.out.println("Application[mysite02] starts..." + contextConfigLocation);
    }
    public void contextDestroyed(ServletContextEvent sce)  { 
    	
    }
	
}
