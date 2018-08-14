package com.test.activiti.serviceexception;

import org.activiti.engine.delegate.DelegateExecution;
import org.apache.log4j.Logger;

import com.test.activiti.BaseJavaDelegate;

public class ExceptionParameter1ServiceTask1 extends BaseJavaDelegate {
	
	Logger logger = Logger.getLogger(ExceptionParameter1ServiceTask1.class);


	@Override
	public void execute(DelegateExecution execution) throws Exception {
		execution.setVariable("VAR", "OK");
		logger.info("VAR put in delegation");

	}

}
