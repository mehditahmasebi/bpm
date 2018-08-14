package com.test.activiti.signalevent2;

import java.util.Map.Entry;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.apache.log4j.Logger;

public class STCatchSignal implements JavaDelegate {

	Logger logger = Logger.getLogger(STCatchSignal.class);
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		logger.info("PDefId: " + execution.getProcessDefinitionId() + " ,PId: " + execution.getProcessInstanceId() + " ,Business Key:" + execution.getProcessBusinessKey());
		logger.info("List of Parameters : ");
		for(Entry<String, Object> pair : execution.getVariables().entrySet())
		{
			logger.info(" --> " + pair.getKey() + ":" + pair.getValue());
		}
	}

}
