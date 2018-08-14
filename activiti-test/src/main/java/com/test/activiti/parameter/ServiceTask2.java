package com.test.activiti.parameter;

import javax.annotation.PostConstruct;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component("serviceTask2")
public class ServiceTask2 implements JavaDelegate {
	
	Logger logger = Logger.getLogger(ServiceTask2.class);

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		logger.info("Object Identity : " + this.toString());
		
		execution.setVariable("ServiceTask2Variable", "A Variable");
	}
	
	@PostConstruct
	public void init()
	{
		logger.info("awServiceTask2 is created");
	}

}
