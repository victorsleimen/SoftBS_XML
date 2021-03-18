package com.softlynx.util;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UtilSpring {

	final static Logger logger = LoggerFactory.getLogger(UtilSpring.class);
	private ClassPathXmlApplicationContext ctx;
	private static ClassPathXmlApplicationContext classPathXmlApplicationContext;

	@Autowired
	private String[] getApplicationContextBeans() throws Exception {

		ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		String[] beanNames = ctx.getBeanDefinitionNames();
		Arrays.sort(beanNames);
		for (String beanName : beanNames) {
			logger.info(beanName);
		}
		return beanNames;
	}

	public static Object getBean(String name) {

		try {
			classPathXmlApplicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
			return classPathXmlApplicationContext.getBean(name);
		} catch (NoSuchBeanDefinitionException ex) {
			return null;
		}
	}

	public static Object getBean(String name, Class<?> cls) {

		try {
			classPathXmlApplicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
			return classPathXmlApplicationContext.getBean(name, cls);
		} catch (NoSuchBeanDefinitionException ex) {
			return null;
		}
	}

}
