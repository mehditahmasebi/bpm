package com.test.activiti.signalevent;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.apache.log4j.Logger;

public class BeforeSignalExecutionListener implements ExecutionListener {
	
	Logger logger = Logger.getLogger(BeforeSignalExecutionListener.class);

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		logger.info("Before signal, Process Instance ID : " + execution.getProcessInstanceId());
	}

}
