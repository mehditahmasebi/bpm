package com.test.activiti.subprocess;

import java.util.Map.Entry;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.apache.log4j.Logger;

public class SubProcessServiceTask extends Object implements JavaDelegate {

	Logger logger = Logger.getLogger(SubProcessServiceTask.class); 
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		logger.info("Sub Process : Service Task");
		
		for(Entry<String, Object> keyValue : execution.getVariables().entrySet())
		{
			logger.info("Variable with name : " + keyValue.getKey() + " is available");
		}
		
		if(execution.hasVariable("var1"))
			logger.info("Super Process Variable var1 : " + execution.getVariable("var1"));
		else
			logger.info("Sub Process does not have Super's Variable var1");
		
		
		logger.info("Sub Process Variable subvar1 (initated from super process): " + execution.getVariable("subvar1"));
		logger.info("Sub Process Variable subvar2 : " + execution.getVariable("subvar2"));
	}

}
