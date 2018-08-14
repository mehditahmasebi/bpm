package com.test.activiti.flow;

import javax.annotation.PostConstruct;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component("ptpNewRequestSendSmsServiceV1")
public class NewRequestSendSmsServiceV2 implements JavaDelegate {

	Logger logger = Logger.getLogger(NewRequestSendSmsServiceV2.class);
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		logger.info("Object Identity : " + this.toString());
	}
	
	@PostConstruct
	public void init()
	{
		logger.info("ptpNewRequestSendSmsServiceV1 is created");
	}

}
