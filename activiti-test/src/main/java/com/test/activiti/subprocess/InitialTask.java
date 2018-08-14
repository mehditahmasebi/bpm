package com.test.activiti.subprocess;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class InitialTask implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {

		execution.setVariable("var1", "Yes this is var 1 value");
	}

}
