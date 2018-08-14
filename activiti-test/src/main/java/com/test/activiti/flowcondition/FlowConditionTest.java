package com.test.activiti.flowcondition;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.log4j.Logger;
import org.junit.Test;

import com.test.activiti.BaseTest;

public class FlowConditionTest extends BaseTest {

	public static final String KEY = "FlowCondition";
	Logger logger = Logger.getLogger(FlowConditionTest.class);
	
	@Override
	protected String[] getBpmnFiles() {
		return new String[] {"com/test/activiti/flowcondition/FlowCondition.bpmn"};
	}
	
	@Test
	public void a_test()
	{
		Map<String, Object> vars = new HashMap<>();
		vars.put("var1", "true");
		vars.put("var2", "true");
		ProcessInstance pi = runtimeService.startProcessInstanceByKey(KEY,vars);
		logger.info("Process Started with Process Instance Id : " + pi.getId());
		
		Task ut1 = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
		taskService.complete(ut1.getId());
		
	}

}
