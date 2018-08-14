package com.test.activiti.serviceexception;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.apache.log4j.Logger;

public class ServiceTask3 implements JavaDelegate {

	Logger logger = Logger.getLogger(ServiceTask3.class);
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		logger.info("Started and Finished");
	}

}
