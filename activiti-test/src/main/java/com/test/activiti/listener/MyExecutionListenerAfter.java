package com.test.activiti.listener;

import java.util.Map.Entry;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.apache.log4j.Logger;

public class MyExecutionListenerAfter implements ExecutionListener {
	private static final long serialVersionUID = 7913045413213246881L;
	Logger logger = Logger.getLogger(MyExecutionListenerAfter.class);

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		logger.info("PID : " + execution.getId());
		logger.info("Execution Parameters : ");
		for(Entry<String, Object> pair : execution.getVariables().entrySet())
		{
			logger.info(" -- Key : " + pair.getKey() + " , Value : " + pair.getValue());
		}
	}

}
