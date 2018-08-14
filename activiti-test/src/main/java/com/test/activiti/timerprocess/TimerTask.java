package com.test.activiti.timerprocess;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class TimerTask implements JavaDelegate
{

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		System.err.println("Timer Task Called, PID: " + execution.getProcessInstanceId());
	}
	
}
