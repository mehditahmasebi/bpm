package com.test.activiti.flowcondition;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.test.activiti.BaseTest;

public class FlowConditionTest6 extends BaseTest {

	public static final String KEY = "FlowCondition6";
	Logger logger = Logger.getLogger(FlowConditionTest6.class);
	
	@Override
	protected String[] getBpmnFiles() {
		return new String[] {"com/test/activiti/flowcondition/FlowCondition6.bpmn"};
	}
	
	@Autowired
	TaskReady taskReady;
	

	@Test
	public void a_test()
	{
		taskReady.setReady(false);
		ProcessInstance pi = runtimeService.startProcessInstanceByKey(KEY);
		
		Task ut1 = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
		
		taskService.complete(ut1.getId());
		
		Task ut3 = taskService.createTaskQuery().processInstanceId(pi.getId()).taskName("UT3").singleResult();
		assertNotNull("bar asase return false taskReady.isready() alan UT3 bayad open bashad",ut3);
	}
	
	@Test
	public void b_test()
	{
		taskReady.setReady(true);
		ProcessInstance pi = runtimeService.startProcessInstanceByKey(KEY);
		
		Task ut1 = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
		
		taskService.complete(ut1.getId());
		
		assertNotNull("Process must be finished", historyService.createHistoricProcessInstanceQuery().processInstanceId(pi.getId()).finished().singleResult());
	}

}