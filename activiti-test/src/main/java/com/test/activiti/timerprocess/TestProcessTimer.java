package com.test.activiti.timerprocess;

import org.activiti.engine.runtime.ProcessInstance;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.test.activiti.BaseTest;
import com.test.activiti.MyProcessEngine;
import com.test.activiti.ProcessDeployer;

public class TestProcessTimer extends BaseTest {
	
	@Autowired(required=true)
	ProcessDeployer processDeployer;
	
	@Autowired
	MyProcessEngine processEngine;
	
	Logger logger = Logger.getLogger(TestProcessTimer.class);
	public static final String NAME = "prc_timer";
	
	@Override
	protected String[] getBpmnFiles() {
		return new String[] {"com/test/activiti/timerprocess/Timer.bpmn"};
	}
	
	@Test
	public void mainScenario()
	{
		logger.info("Main Scenario -- Started : Create a process instnce");
		ProcessInstance processInstance = processEngine.getProcessEngine().getRuntimeService().startProcessInstanceByKey(NAME);
		logger.info("New Process Instance created with id : " + processInstance.getId());
	}

	@Test
	public void threadScenario()
	{
		try {
			Thread.sleep(125000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	
	@After
	public void after()
	{
		logger.info("Test Process Timer -- Finished");
	}


}
