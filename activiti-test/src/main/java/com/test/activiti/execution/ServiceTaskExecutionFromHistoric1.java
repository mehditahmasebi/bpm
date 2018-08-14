package com.test.activiti.execution;

import org.activiti.engine.delegate.DelegateExecution;
import org.apache.log4j.Logger;
import org.junit.Assert;

import com.test.activiti.BaseJavaDelegate;

public class ServiceTaskExecutionFromHistoric1 extends BaseJavaDelegate {

	Logger logger = Logger.getLogger(ServiceTaskExecutionFromHistoric1.class);
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		logger.info("At ServiceTaskExecutionFromRuntime execute method");
		// dar historic var1 = null
		Assert.assertNull("var1 is not found at historic",historyService.createHistoricVariableInstanceQuery().processInstanceId(execution.getProcessInstanceId()).variableName("var1").singleResult());
	}

}
