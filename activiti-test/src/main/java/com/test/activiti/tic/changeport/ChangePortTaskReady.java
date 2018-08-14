package com.test.activiti.tic.changeport;

import org.activiti.engine.delegate.DelegateExecution;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component("changePortTaskReady")
public class ChangePortTaskReady {
	
	private boolean conditionResult = false;
	
	Logger logger = Logger.getLogger(ChangePortTaskReady.class);

	public boolean isReady(String name, DelegateExecution exec)
	{
		logger.info("Process Definition : " + exec.getProcessDefinitionId()  + " , and Condition Result = " + conditionResult);
		return conditionResult;
	}

	public boolean isConditionResult() {
		return conditionResult;
	}

	public void setConditionResult(boolean conditionResult) {
		this.conditionResult = conditionResult;
	}
}
