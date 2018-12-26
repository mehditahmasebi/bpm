package com.test.activiti.listener;

import org.activiti.engine.runtime.ProcessInstance;
import org.apache.log4j.Logger;
import org.junit.Test;

import com.test.activiti.BaseTest;

public class SampleEventProcessTest extends BaseTest {
	
	public static final String KEY = "SampleEventProcess";
	Logger logger = Logger.getLogger(SampleEventProcessTest.class);

	@Override
	protected String[] getBpmnFiles() {
		return new String[] {
				"com/test/activiti/listener/SampleEventProcess.bpmn"	
			};
	}
	
	@Test
	public void test_1()
	{
		runtimeService.addEventListener(new MyActivitiEventListenerForServiceTask());
		ProcessInstance pi = runtimeService.startProcessInstanceByKey(KEY);
		taskService.complete(taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult().getId());
	}


}
