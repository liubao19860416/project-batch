package com.saike.grape.callcenter.sevice.impl;


import com.meidusa.toolkit.common.runtime.Application;
import com.meidusa.toolkit.common.runtime.ApplicationConfig;


public class SignBackApplication extends Application<ApplicationConfig>{

	@Override
	public void doRun() {
		
	}

	@Override
	public ApplicationConfig getApplicationConfig() {
		return null;
	}

	@Override
	protected String[] getConfigLocations() {
		return new String[] { "file:${project.home}/conf/applicationContext-batch-server.xml"};
	}
	
	public static void main(String[] args) {
		System.setProperty(ApplicationConfig.PROJECT_MAINCLASS, SignBackApplication.class.getName());
		Application.main(args);
	}

}
