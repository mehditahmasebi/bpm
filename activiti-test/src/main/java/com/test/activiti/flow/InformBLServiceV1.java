package com.test.activiti.flow;

import javax.annotation.PostConstruct;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component("ptpInformBLServiceV1")
public class InformBLServiceV1 implements JavaDelegate {

	Logger logger = Logger.getLogger(InformBLServiceV1.class);
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		logger.info("Object Identity : " + this.toString());
	}
	
	@PostConstruct
	public void init()
	{
		logger.info("ptpInformBLServiceV1 is created");
	}

}
