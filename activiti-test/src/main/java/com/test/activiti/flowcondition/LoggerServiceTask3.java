package com.test.activiti.flowcondition;

import org.activiti.engine.delegate.DelegateExecution;
import org.apache.log4j.Logger;

import com.test.activiti.BaseJavaDelegate;

public class LoggerServiceTask3 extends BaseJavaDelegate {

	Logger logger = Logger.getLogger(LoggerServiceTask3.class);
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		logger.info("ProcessDefinition ID : " + execution.getProcessDefinitionId());
	}

}
