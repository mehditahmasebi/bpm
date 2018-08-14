package com.test.activiti.execution;

import org.activiti.engine.delegate.DelegateExecution;
import org.apache.log4j.Logger;
import org.junit.Assert;

import com.test.activiti.BaseJavaDelegate;

public class ServiceTaskExecution1 extends BaseJavaDelegate {
	
	Logger logger = Logger.getLogger(ServiceTaskExecution1.class);

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		logger.info("At ServiceTaskExecution1 execute method");
		Assert.assertNotNull(runtimeService.getVariable(execution.getId(), "var1"));
	}

}
