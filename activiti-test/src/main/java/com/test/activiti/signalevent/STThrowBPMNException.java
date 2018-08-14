package com.test.activiti.signalevent;

import org.activiti.engine.delegate.BpmnError;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class STThrowBPMNException implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		throw new BpmnError("my error");
	}

}
