package com.test.activiti.signalevent;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.apache.log4j.Logger;

public class SimpleServiceTask implements JavaDelegate {
	
	Logger logger = Logger.getLogger(SimpleServiceTask.class);
	public static String THROW_EVENT = "THROW_EVENT";
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		logger.info("in Service Task , Process Instance ID : " + execution.getProcessInstanceId() + " Execution ID : " + execution.getId()  + " Process Def Id : " + execution.getProcessDefinitionId() + " Task name : " + execution.getCurrentActivityName());
		execution.setVariable(THROW_EVENT, (Integer.parseInt(execution.getProcessInstanceId()) > 30) ? true : false);
	}

}
