package com.test.activiti.signalevent;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.apache.log4j.Logger;

public class AfterCatchedSignalServiceTask implements JavaDelegate {
	Logger logger = Logger.getLogger(AfterCatchedSignalServiceTask.class);

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		logger.info("Signal Called:: now in " + execution.getProcessDefinitionId() +  " , Process Instance ID : " + execution.getProcessInstanceId() + " Execution ID : " + execution.getId() + " Task Name : " + execution.getCurrentActivityName());
	}

}
