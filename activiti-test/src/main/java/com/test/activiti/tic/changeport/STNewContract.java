package com.test.activiti.tic.changeport;

import org.activiti.engine.delegate.BpmnError;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component("stNewContract")
public class STNewContract implements JavaDelegate {

	Logger logger = Logger.getLogger(STNewContract.class);
	
	private boolean throwError = false;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		try {
			logger.info("Process instance : " + execution.getProcessInstanceId() + " New Contract CITems has been Created successfully");
			if (isThrowError())
				throw new BpmnError("nothing", "Error Message");
		} catch (Exception e) {
			throw new BpmnError("nothing", e.getMessage());
		}
	}

	public boolean isThrowError() {
		return throwError;
	}

	public void setThrowError(boolean throwError) {
		this.throwError = throwError;
	}
}
