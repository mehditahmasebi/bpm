package com.test.activiti.autowiredservicetask;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.test.activiti.BaseTest;

public class TestAWServiceTask extends BaseTest {
	
	Logger logger = Logger.getLogger(TestAWServiceTask.class);
	public static String KEY = "awProcess";
	
	@Test
	public void autoWireTest()
	{
		processDeployer.printAllProcessDefinition();
		processEngine.getProcessEngine().getRuntimeService().startProcessInstanceByKey(KEY);
	}
	@Override
	protected String[] getBpmnFiles() {
		return new String[] {
				"com/test/activiti/autowiredservicetask/AutoWireServiceTask.bpmn"};
	}

}
