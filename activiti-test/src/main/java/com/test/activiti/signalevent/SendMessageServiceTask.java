package com.test.activiti.signalevent;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.test.activiti.MyProcessEngine;

@Component("sendMessageServiceTask")
public class SendMessageServiceTask implements JavaDelegate {

	@Autowired
	MyProcessEngine processEngine;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		processEngine.getProcessEngine().getRuntimeService().startProcessInstanceByMessage("startmsg");
	}

}
