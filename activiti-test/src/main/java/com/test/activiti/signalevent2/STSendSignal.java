package com.test.activiti.signalevent2;

import java.util.HashMap;
import java.util.List;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.runtime.Execution;
import org.apache.log4j.Logger;

import com.test.activiti.BaseJavaDelegate;

public class STSendSignal extends BaseJavaDelegate {

	Logger logger = Logger.getLogger(STSendSignal.class);
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		logger.info("PDefId : " + execution.getProcessDefinitionId() + " ,PId: " + execution.getProcessInstanceId() + " ,Business Key:" + execution.getProcessBusinessKey());
		
		HashMap<String,Object> params = new HashMap<String, Object>();
		params.put("oldProcessId", execution.getProcessInstanceId());
		
		List<Execution> executions = runtimeService.createExecutionQuery().signalEventSubscriptionName("startsignal").list();
		logger.info("Execution List number : " + (executions == null ? "null" : executions.size()));
		runtimeService.signalEventReceived("startsignal",params);		
	}

}
