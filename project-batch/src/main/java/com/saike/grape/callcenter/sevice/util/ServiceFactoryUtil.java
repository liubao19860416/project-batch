package com.saike.grape.callcenter.sevice.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.meidusa.venus.client.ServiceFactory;

public class ServiceFactoryUtil {

	private static ServiceFactory serviceFactory = null;
	private static ApplicationContext ctx = null;

	public static ApplicationContext getApplicationContext() {
		if (ctx == null) {
			try {
				ctx = new FileSystemXmlApplicationContext(
						"file:${project.home}/conf/applicationContext-batch-client.xml");
			} catch (Exception e) {
				e.printStackTrace();
				ctx = new FileSystemXmlApplicationContext(
						"/conf/applicationContext-batch-client.xml");
				
			}
		}
		return ctx;
	}

	public static ServiceFactory getServiceFactory() {
		if (serviceFactory == null) {
			serviceFactory = (ServiceFactory) (getApplicationContext()
					.getBean("serviceFactory"));
		}
		return serviceFactory;
	}

}
