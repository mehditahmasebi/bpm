package com.test.activiti.flow;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import com.test.activiti.BaseTest;

public class FlowTest1 extends BaseTest {

	Logger logger = Logger.getLogger(FlowTest1.class);
	public static final String KEY = "PTP_SERVICE";
	
	@Override
	protected String[] getBpmnFiles() {
		return new String[] {"com/test/activiti/flow/Flow1.bpmn"};
	}
	
	@Test
	public void testIsMahroom()
	{
		Map<String, Object> vars = new HashMap<String, Object>();
		vars.put("PTP_BL_RESULT", false);
		vars.put("PTP_REQUEST_IS_MAHROOM", true);
		
		ProcessInstance pi = runtimeService.startProcessInstanceByKey(KEY,vars);
		logger.info("PID : " + pi.getId());
		
		Task ut_PTP_CREDIT_CHECK = taskService.createTaskQuery().processInstanceId(pi.getId()).taskDefinitionKey("PTP_CREDIT_CHECK").singleResult();
		Task ut_PTP_TECHNICAL_VALIDATION = taskService.createTaskQuery().processInstanceId(pi.getId()).taskDefinitionKey("PTP_TECHNICAL_VALIDATION").singleResult();
		Task ut_PTP_SOURCE_CHECK = taskService.createTaskQuery().processInstanceId(pi.getId()).taskDefinitionKey("PTP_SOURCE_CHECK").singleResult();
		Assert.assertNotNull(ut_PTP_CREDIT_CHECK);
		Assert.assertNotNull(ut_PTP_TECHNICAL_VALIDATION);
		Assert.assertNotNull(ut_PTP_SOURCE_CHECK);
		
		vars.put("PTP_TECHNICAL_VALIDATION_RESULT", "accept");
		vars.put("PTP_CREDIT_CHECK_RESULT", true);
		
		taskService.complete(ut_PTP_SOURCE_CHECK.getId(), vars);
		
		Assert.assertNotNull(taskService.createTaskQuery().processInstanceId(pi.getId()).taskDefinitionKey("PTP_TECHNICAL_VALIDATION").singleResult());
		Assert.assertNotNull(taskService.createTaskQuery().processInstanceId(pi.getId()).taskDefinitionKey("PTP_CREDIT_CHECK").singleResult());
		Assert.assertNull(historyService.createHistoricActivityInstanceQuery().processInstanceId(pi.getId()).activityId("PTP_SEND_SERVICE_FORM").singleResult());
		taskService.complete(ut_PTP_TECHNICAL_VALIDATION.getId(), vars);
		
		
		Assert.assertNotNull(taskService.createTaskQuery().processInstanceId(pi.getId()).taskDefinitionKey("PTP_CREDIT_CHECK").singleResult());
		Assert.assertNull(historyService.createHistoricActivityInstanceQuery().processInstanceId(pi.getId()).activityId("PTP_SEND_SERVICE_FORM").singleResult());
		taskService.complete(ut_PTP_CREDIT_CHECK.getId(), vars);
		
		
		Assert.assertNotNull(historyService.createHistoricActivityInstanceQuery().processInstanceId(pi.getId()).activityId("PTP_SEND_SERVICE_FORM").singleResult());
		
		
		
	}
	@Test
	public void testIsNotMahroom()
	{
		Map<String, Object> vars = new HashMap<String, Object>();
		vars.put("PTP_BL_RESULT", false);
		vars.put("PTP_REQUEST_IS_MAHROOM", false);
		
		ProcessInstance pi = runtimeService.startProcessInstanceByKey(KEY,vars);
		logger.info("PID : " + pi.getId());
		
		Task ut_PTP_CREDIT_CHECK = taskService.createTaskQuery().processInstanceId(pi.getId()).taskDefinitionKey("PTP_CREDIT_CHECK").singleResult();
		Task ut_PTP_TECHNICAL_VALIDATION = taskService.createTaskQuery().processInstanceId(pi.getId()).taskDefinitionKey("PTP_TECHNICAL_VALIDATION").singleResult();
		Task ut_PTP_SOURCE_CHECK = taskService.createTaskQuery().processInstanceId(pi.getId()).taskDefinitionKey("PTP_SOURCE_CHECK").singleResult();
		Assert.assertNotNull(ut_PTP_CREDIT_CHECK);
		Assert.assertNotNull(ut_PTP_TECHNICAL_VALIDATION);
		Assert.assertNull(ut_PTP_SOURCE_CHECK);
		
		vars.put("PTP_TECHNICAL_VALIDATION_RESULT", "accept");
		vars.put("PTP_CREDIT_CHECK_RESULT", true);
		
		taskService.complete(ut_PTP_CREDIT_CHECK.getId(), vars);
		Assert.assertNotNull(taskService.createTaskQuery().processInstanceId(pi.getId()).taskDefinitionKey("PTP_TECHNICAL_VALIDATION").singleResult());
		
		taskService.complete(ut_PTP_TECHNICAL_VALIDATION.getId(), vars);
		Assert.assertNotNull(historyService.createHistoricActivityInstanceQuery().processInstanceId(pi.getId()).activityId("PTP_SEND_SERVICE_FORM").singleResult());
		
		
		
	}

}
