package com.test.activiti.message;

import java.util.Map.Entry;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.apache.log4j.Logger;

public class STCatchMessage implements JavaDelegate {

	Logger logger = Logger.getLogger(STCatchMessage.class);
	
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
