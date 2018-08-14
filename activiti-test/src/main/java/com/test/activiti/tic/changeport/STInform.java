package com.test.activiti.tic.changeport;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.apache.log4j.Logger;

public class STInform implements JavaDelegate {

	Logger logger = Logger.getLogger(STInform.class);
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		logger.info("Process instance : " + execution.getProcessInstanceId() + " All candidate user has been informed");
	}

}
