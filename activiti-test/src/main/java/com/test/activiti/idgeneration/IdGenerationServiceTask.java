package com.test.activiti.idgeneration;

import org.activiti.engine.delegate.DelegateExecution;
import org.apache.log4j.Logger;

import com.test.activiti.BaseJavaDelegate;

public class IdGenerationServiceTask extends BaseJavaDelegate {
	
	Logger logger = Logger.getLogger(IdGenerationServiceTask.class);

	@Override
	public void execute(DelegateExecution execution) throws Exception {

		logger.info("In Start : Execution Id  = " + execution.getProcessInstanceId());
		logger.info("going to exception");
		throw new RuntimeException("exception to rollback new process instance");
	}

}
