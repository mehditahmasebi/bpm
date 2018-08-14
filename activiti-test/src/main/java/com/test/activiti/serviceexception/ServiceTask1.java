package com.test.activiti.serviceexception;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.apache.log4j.Logger;

public class ServiceTask1 implements JavaDelegate{
	
	Logger logger = Logger.getLogger(ServiceTask1.class);

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		logger.info("Start");
		logger.info("Commit");
	}

}
