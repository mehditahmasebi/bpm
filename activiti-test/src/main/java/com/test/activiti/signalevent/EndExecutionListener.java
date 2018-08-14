package com.test.activiti.signalevent;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.apache.log4j.Logger;

public class EndExecutionListener implements ExecutionListener {

	private static final long serialVersionUID = 6307142204932063459L;
	Logger logger = Logger.getLogger(EndExecutionListener.class);
	@Override
	public void notify(DelegateExecution execution) throws Exception {
		logger.info("Process instance : " + execution.getProcessInstanceId() + " end after throw Signal");
	}

}
