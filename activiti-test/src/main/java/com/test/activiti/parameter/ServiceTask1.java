package com.test.activiti.parameter;

import javax.annotation.PostConstruct;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component("serviceTask1")
public class ServiceTask1 implements JavaDelegate {

	Logger logger = Logger.getLogger(ServiceTask1.class);
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		logger.info("Object Identity : " + this.toString());
	}
	
	@PostConstruct
	public void init()
	{
		logger.info("awServiceTask1 is created");
	}

}
