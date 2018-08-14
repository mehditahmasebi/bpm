package com.test.activiti.signalevent;

import org.activiti.engine.runtime.ProcessInstance;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Test;

import com.test.activiti.BaseTest;

public class TestErrorEvent_WithSubProcess3 extends BaseTest {
	
	Logger logger = Logger.getLogger(TestErrorEvent_WithSubProcess3.class);
	public static String KEY = "ErrorEvent_WithSubProcess";

	@Override
	protected String[] getBpmnFiles() {
		return new String[] {"com/test/activiti/signalevent/ErrorEvent_WithSubProcess3.bpmn"};
	}

	@Test
	public void test_subprocess() throws InterruptedException
	{
		for(int i=0;i<1;i++)
		{
			ProcessInstance pi =runtimeService.startProcessInstanceByKey(KEY); 
			logger.info("New Process Instance By id : " + pi.getId() + " Process Def Name : " + pi.getProcessDefinitionName());
		}
		
		Thread.sleep(10000);
	}
	
	
	@After
	public void after()
	{
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("Test is finished");
	}
}
