package com.lonsec.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * The application context helper class. This class is used to initialize the
 * spring application context.
 *
 */
@Configuration
@ComponentScan
public class AppContext {
	private static ApplicationContext context = null;
	
	public static ApplicationContext getContext() {
		if(context == null)
			context = new ClassPathXmlApplicationContext("system-config.xml", AppContext.class);
		
		return context;
	}
}
