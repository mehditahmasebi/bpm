package com.test.activiti.serviceexception;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.apache.log4j.Logger;

public class ServiceTask2Ex implements JavaDelegate {
	
	Logger logger = Logger.getLogger(ServiceTask2Ex.class);

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		logger.info("Start");
		logger.info("Try to commit but throw runtime exception");
		throw new RuntimeException("intentionally throw a runtime exception");
	}

}
