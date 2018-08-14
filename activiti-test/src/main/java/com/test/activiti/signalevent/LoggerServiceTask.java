package com.test.activiti.signalevent;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class LoggerServiceTask implements JavaDelegate {

	org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(LoggerServiceTask.class);
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		logger.info("ProcessDefinition ID : " + execution.getProcessDefinitionId());
	}

}
