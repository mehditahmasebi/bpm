package com.test.activiti.serviceexception;

import org.activiti.engine.delegate.DelegateExecution;
import org.apache.log4j.Logger;

import com.test.activiti.BaseJavaDelegate;

public class ExceptionParameter1ServiceTask2 extends BaseJavaDelegate {

	Logger logger = Logger.getLogger(ExceptionParameter1ServiceTask2.class);
	
	@Override
	public void execute(final DelegateExecution execution) throws Exception {
		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				runtimeService.setVariable(execution.getProcessInstanceId(),"VAR", "OK");
			}
		});
		thread.start();
		thread.join();
		logger.info("VAR put in runtimeService");
	}

}
