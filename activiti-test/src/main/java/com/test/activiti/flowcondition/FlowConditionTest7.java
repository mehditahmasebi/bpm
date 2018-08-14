package com.test.activiti.flowcondition;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.runtime.ProcessInstance;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import com.test.activiti.BaseTest;

public class FlowConditionTest7 extends BaseTest {

	Logger logger = Logger.getLogger(FlowConditionTest7.class);
	public static final String KEY = "FlowCondition7";
	
	@Override
	protected String[] getBpmnFiles() {
		return new String[] {"com/test/activiti/flowcondition/FlowCondition7.bpmn"};
	}
	
	@Test
	public void test()
	{
		Map<String, Object> vars = new HashMap<>();
		vars.put("param", false);
		ProcessInstance pi = runtimeService.startProcessInstanceByKey(KEY);
		logger.info("PID: " + pi.getId());;
		
		Assert.assertNotNull(historyService.createHistoricProcessInstanceQuery().processInstanceId(pi.getId()).finished().singleResult());
	}

}
