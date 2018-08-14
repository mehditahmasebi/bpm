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

public class FeasibilityTest3 extends BaseTest {
	
	public static final String KEY = "FeasibilityKEY3";
	Logger logger = Logger.getLogger(FeasibilityTest3.class);

	@Override
	protected String[] getBpmnFiles() {
		return new String[] {"com/test/activiti/gateway/Feasibility3.bpmn"};
	}
	
	@Test
	public void a_test()
	{
		ProcessInstance pi = runtimeService.startProcessInstanceByKey(KEY);
		
		List<Task> userTasks = taskService.createTaskQuery().processInstanceId(pi.getId()).list();
		assertEquals(3, userTasks.size());
		logger.info("All Task is get");
		
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("FT1", "false");
		taskService.complete(userTasks.get(0).getId(),map1);
		logger.info("Task 0 completed");
		
		assertEquals(null, runtimeService.createProcessInstanceQuery().processInstanceId(pi.getId()).singleResult());
		
		
		logger.info("All User Task had completed");
		
	}
	
	@Test
	public void b_test()
	{
		ProcessInstance pi = runtimeService.startProcessInstanceByKey(KEY);
		
		List<Task> userTasks = taskService.createTaskQuery().processInstanceId(pi.getId()).list();
		assertEquals(3, userTasks.size());
		logger.info("All Task is get");
		
		Map<String, Object> map1 = new HashMap<String, Object>();
		//False budane zir masir ra taghyir midahad, va service task ejra nemshavad
		map1.put("FT1", "false");
		taskService.complete(userTasks.get(0).getId(),map1);
		logger.info("Task 0 completed");
		
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("FT2", "true");
		taskService.complete(userTasks.get(1).getId(),map2);
		logger.info("Task 1 completed");
		
		Map<String, Object> map3 = new HashMap<String, Object>();
		map3.put("FT3", "false");
		taskService.complete(userTasks.get(2).getId(),map3);
		logger.info("Task 2 completed");
		
		logger.info("All User Task had completed");
		
	}
	
	@Test
	public void c_test()
	{
		ProcessInstance pi = runtimeService.startProcessInstanceByKey(KEY);
		
		List<Task> userTasks = taskService.createTaskQuery().processInstanceId(pi.getId()).list();
		assertEquals(3, userTasks.size());
		logger.info("All Task is get");
		
		Map<String, Object> map1 = new HashMap<String, Object>();
		//hame true hastand v service Task ejra mishavad
		map1.put("FT1", "true");
		taskService.complete(userTasks.get(0).getId(),map1);
		logger.info("Task 0 completed");
		
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("FT2", "true");
		taskService.complete(userTasks.get(1).getId(),map2);
		logger.info("Task 1 completed");
		
		Map<String, Object> map3 = new HashMap<String, Object>();
		map3.put("FT3", "false");
		taskService.complete(userTasks.get(2).getId(),map3);
		logger.info("Task 2 completed");
		
		logger.info("All User Task had completed");
		
	}

}
