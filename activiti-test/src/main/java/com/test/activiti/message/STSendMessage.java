package com.test.activiti.message;

import java.util.HashMap;

import org.activiti.engine.delegate.DelegateExecution;
import org.apache.log4j.Logger;

import com.test.activiti.BaseJavaDelegate;

public class STSendMessage extends BaseJavaDelegate {

	Logger logger = Logger.getLogger(STSendMessage.class);
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		logger.info("PDefId : " + execution.getProcessDefinitionId() + " ,PId: " + execution.getProcessInstanceId() + " ,Business Key:" + execution.getProcessBusinessKey());
		
		HashMap<String,Object> params = new HashMap<String, Object>();
		params.put("oldProcessId", execution.getProcessInstanceId());
		
		runtimeService.startProcessInstanceByMessage("startmsg", execution.getProcessBusinessKey(),params);		
	}

}
