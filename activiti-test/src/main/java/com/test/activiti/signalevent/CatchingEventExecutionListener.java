package com.test.activiti.signalevent;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.apache.log4j.Logger;

public class CatchingEventExecutionListener implements ExecutionListener {

	private static final long serialVersionUID = 6307142204932063459L;
	Logger logger = Logger.getLogger(CatchingEventExecutionListener.class);
	@Override
	public void notify(DelegateExecution execution) throws Exception {
		logger.info("Process instance : " + execution.getProcessInstanceId() + " is at Start Signal Event");
	}

}
