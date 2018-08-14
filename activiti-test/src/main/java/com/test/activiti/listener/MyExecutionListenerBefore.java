package com.test.activiti.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.apache.log4j.Logger;

public class MyExecutionListenerBefore implements ExecutionListener {
	private static final long serialVersionUID = 7913045413213246881L;
	Logger logger = Logger.getLogger(MyExecutionListenerBefore.class);

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		logger.info("PID : " + execution.getId() + " , and set a param (Param1)");
		execution.setVariable("Param1", "something");
	}

}
