package com.test.activiti.signalevent;

import org.activiti.engine.runtime.ProcessInstance;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Test;

import com.test.activiti.BaseTest;

public class TestMessageEvent2 extends BaseTest {
	
	Logger logger = Logger.getLogger(TestMessageEvent2.class);

	@Override
	protected String[] getBpmnFiles() {
		return new String[] {"com/test/activiti/signalevent/MessageEvent.bpmn"};
	}

	@Test
	public void test_subprocess() throws InterruptedException
	{
		for(int i=0;i<10;i++)
		{
			ProcessInstance pi =runtimeService.startProcessInstanceByKey("MessageEvent"); 
			logger.info("New Process Instance By id : " + pi.getId());
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
