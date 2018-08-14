package com.test.activiti.timerprocess;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.apache.log4j.Logger;

public class LoggerServiceTask implements JavaDelegate {
	
	Logger logger =Logger.getLogger(LoggerServiceTask.class);

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		logger.info("ProcessDefinition ID : " + execution.getProcessDefinitionId() + " ,Process Instance ID : " + execution.getId());
	}

}
