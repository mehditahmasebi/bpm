package com.test.activiti.serviceexception;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Assert;
import org.junit.Test;

import com.test.activiti.BaseTest;

/**
 * Related to BehindUserTask1
 * @author Mehdi
 *
 */
public class TestServiceException4 extends BaseTest {
	
	public static final String KEY = "Exception4";

	@Override
	protected String[] getBpmnFiles() {
		return new String[] {"com/test/activiti/serviceexception/Exception4.bpmn"};
	}
	
	@Test
	public void assigneeException()
	{
		ProcessInstance pi = runtimeService.startProcessInstanceByKey(KEY);
		Task ut1 = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
		taskService.setAssignee(ut1.getId(), "Mehdi");
		try {
			taskService.complete(ut1.getId());
			
		} catch (Exception e) {
			
			ut1 = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
			Assert.assertNotNull(ut1.getAssignee());
			return;
		}
		Assert.assertNotNull("nabayad code be inja miresid", null);
		
	}

}
