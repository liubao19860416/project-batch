package com.saike.grape.callcenter.sevice.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import com.gagu.ucm.agent.venus.placeholder.VenusPropertyPlaceholderConfigurer;

public class CustomizedPropertyPlaceholderConfigurer extends  VenusPropertyPlaceholderConfigurer {
	public static Map<String, String> ctxPropertiesMap;

	@Override
	protected void processProperties(
			ConfigurableListableBeanFactory beanFactoryToProcess,
			Properties props) throws BeansException {
		super.processProperties(beanFactoryToProcess, props);
		ctxPropertiesMap = new HashMap<String, String>();
		for (Object key : props.keySet()) {
			String keyStr = key.toString();
			String value = props.getProperty(keyStr);
			ctxPropertiesMap.put(keyStr, value);
		}
	}

	public static String getContextProperty(String name) {
		return ctxPropertiesMap.get(name);
	}

}
