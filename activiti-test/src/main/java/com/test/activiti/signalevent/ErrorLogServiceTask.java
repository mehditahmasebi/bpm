package com.test.activiti.signalevent;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.apache.log4j.Logger;

public class ErrorLogServiceTask implements JavaDelegate {
	Logger logger = Logger.getLogger(ErrorLogServiceTask.class);
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		logger.info("Error Catched :: now in " + execution.getProcessDefinitionId() +  " , Process Instance ID : " + execution.getProcessInstanceId() + " Execution ID : " + execution.getId());
	}

}
