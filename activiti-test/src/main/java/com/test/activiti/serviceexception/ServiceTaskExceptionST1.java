package com.test.activiti.serviceexception;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class ServiceTaskExceptionST1 implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		throw new RuntimeException("throw exception for monitor last user task assignee");
	}

}
