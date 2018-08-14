package com.test.activiti.subprocess;

import org.activiti.engine.runtime.ProcessInstance;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Test;

import com.test.activiti.BaseTest;

public class TestSubProcess extends BaseTest {
	
	Logger logger = Logger.getLogger(TestSubProcess.class);
	public static final String NAME = "superProcess";
	
	@Override
	protected String[] getBpmnFiles() {
		return new String[] {
				"com/test/activiti/subprocess/SuperProcess.bpmn",
				"com/test/activiti/subprocess/SubProcess.bpmn"};
	}
	
	
	@Test
	public void b_printAllProcessDefinitions()
	{
		processDeployer.printAllProcessDefinition();
	}
	
	@Test
	public void b_mainScenario()
	{
		logger.info("Main Scenario -- Started : Create a process instnce");		
		ProcessInstance processInstance = processEngine.getProcessEngine().getRuntimeService().startProcessInstanceByKey(NAME);
		logger.info("New Process Instance created with id : " + processInstance.getId());
	}

	
	@After
	public void after()
	{
		logger.info("Super Process -- Finished");
	}
}
