package com.test.activiti.autowiredservicetask;

import javax.annotation.PostConstruct;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component("awServiceTask1")
public class AWServiceTask1 implements JavaDelegate {

	Logger logger = Logger.getLogger(AWServiceTask1.class);
	
	//Process Engine Autowirable
	
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
