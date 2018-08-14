package com.test.activiti.gateway;

import java.util.Map;

import org.activiti.engine.delegate.DelegateExecution;
import org.apache.log4j.Logger;

import com.test.activiti.BaseJavaDelegate;

public class LoggerServiceTask extends BaseJavaDelegate {

	Logger logger = Logger.getLogger(LoggerServiceTask.class);
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		logger.info("ProcessDefinition ID : " + execution.getProcessDefinitionId());
		execution.setVariable("ST", "Executed");
		
		Map<String, Object> vars = runtimeService.getVariables(execution.getId());
		
		for(Map.Entry<String, Object> pair : vars.entrySet())
		{
			logger.info("Variable - " + pair.getKey() + ":" + pair.getValue());
		}
	}

}
