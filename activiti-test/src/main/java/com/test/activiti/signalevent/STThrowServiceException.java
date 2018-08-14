package com.test.activiti.signalevent;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.apache.log4j.Logger;

public class STThrowServiceException implements JavaDelegate {
	
	Logger logger = Logger.getLogger(STThrowServiceException.class);

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		throw new RuntimeException("Intentinally exception");
	}

}
