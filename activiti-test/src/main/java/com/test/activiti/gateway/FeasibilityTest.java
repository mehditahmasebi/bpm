package com.test.activiti.gateway;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.log4j.Logger;
import org.junit.Test;

import com.test.activiti.BaseTest;

public class FeasibilityTest extends BaseTest {
	
	public static final String KEY = "FeasibilityKEY";
	Logger logger = Logger.getLogger(FeasibilityTest.class);

	@Override
	protected String[] getBpmnFiles() {
		return new String[] {"com/test/activiti/gateway/Feasibility.bpmn"};
	}
	
	@Test
	public void a_test()
	{
		ProcessInstance pi = runtimeService.startProcessInstanceByKey(KEY);
		
		List<Task> userTasks = taskService.createTaskQuery().processInstanceId(pi.getId()).list();
		assertEquals(3, userTasks.size());
		logger.info("Number of executions : " + runtimeService.createExecutionQuery().processInstanceId(pi.getId()).count());
		
		logger.info("All Task is get");
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("FT1", "true");
		taskService.complete(userTasks.get(0).getId(),map1);
		logger.info("Task 0 completed");
		assertEquals(4, runtimeService.createExecutionQuery().processInstanceId(pi.getId()).count());
		
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("FT2", "true");
		taskService.complete(userTasks.get(1).getId(),map2);
		logger.info("Task 1 completed");
		assertEquals(4, runtimeService.createExecutionQuery().processInstanceId(pi.getId()).count());

		
		Map<String, Object> map3 = new HashMap<String, Object>();
		map3.put("FT3", "true");
		taskService.complete(userTasks.get(2).getId(),map3);
		//daghighan ba khate bala aval service task ba'di ejra mishavad ba'd khate payini ejra mishavad, pas nabayad shob'he shavad ke service task zoodtar ejra mishavad
		logger.info("Task 2 completed");
		
		logger.info("All User Task had completed");
		
	}
	

}
