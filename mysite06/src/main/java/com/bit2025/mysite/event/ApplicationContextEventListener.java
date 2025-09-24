package com.bit2025.mysite.event;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

import com.bit2025.mysite.service.SiteService;
import com.bit2025.mysite.vo.SiteVo;

public class ApplicationContextEventListener {
	private ApplicationContext applicationContext;
	
	public ApplicationContextEventListener(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}
	
	@EventListener({ContextRefreshedEvent.class})
	public void handlerContextRefreshedEvent() {
		System.out.println("-- Context Refreshed Event Received --");
		
		SiteService siteService = applicationContext.getBean(SiteService.class);
		SiteVo vo = siteService.getSite();
		
		MutablePropertyValues propertyValues = new MutablePropertyValues();
		propertyValues.add("id", vo.getId());
		propertyValues.add("title", vo.getTitle());
		propertyValues.add("welcomeMessage", vo.getWelcomeMessage());
		propertyValues.add("description", vo.getDescription());
		propertyValues.add("profileURL", vo.getProfileURL());
		
		GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
		beanDefinition.setBeanClass(SiteVo.class);
		beanDefinition.setPropertyValues(propertyValues);
		
		BeanDefinitionRegistry registry = (BeanDefinitionRegistry)applicationContext.getAutowireCapableBeanFactory();
		registry.registerBeanDefinition("site", beanDefinition);
	}
}
