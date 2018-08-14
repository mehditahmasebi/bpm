package com.test.activiti.tic.changeport;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.apache.log4j.Logger;

public class STSendSMS implements JavaDelegate {

	Logger logger = Logger.getLogger(STSendSMS.class);
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		logger.info("Process instance : " + execution.getProcessInstanceId() + " SMS Sent");
	}

}
