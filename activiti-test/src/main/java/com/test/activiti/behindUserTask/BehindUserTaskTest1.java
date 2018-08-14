package com.test.activiti.behindUserTask;

import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Assert;
import org.junit.Test;

import com.test.activiti.BaseTest;

/**
 * related to Exception4 Test
 * @author Mehdi
 *
 */
public class BehindUserTaskTest1 extends BaseTest {
	
	public static final String KEY = "BehindUserTask1";

	@Override
	protected String[] getBpmnFiles() {
		return new String[] {"com/test/activiti/behindUserTask/BehindUserTask1.bpmn"};
	}
	
	@Test
	public void withoutException()
	{
		clearSingletonBeans();
		registerSingletonBean("stexecution1", new STBehind1());
		ProcessInstance pi = runtimeService.startProcessInstanceByKey(KEY);
		Task ut1 = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
		taskService.setAssignee(ut1.getId(), "Mehdi");
		try {
			taskService.complete(ut1.getId());
			
		} catch (Exception e) {
		}
		HistoricTaskInstance hut1 = historyService.createHistoricTaskInstanceQuery().processInstanceId(pi.getId()).singleResult();
		Assert.assertEquals("Ali", hut1.getAssignee());
	}
	
	@Test
	public void withException()
	{
		clearSingletonBeans();
		registerSingletonBean("stexecution1", new STBehindWithError1());
		ProcessInstance pi = runtimeService.startProcessInstanceByKey(KEY);
		Task ut1 = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
		taskService.setAssignee(ut1.getId(), "Mehdi");
		try {
			taskService.complete(ut1.getId());
			
		} catch (Exception e) {
			
			ut1 = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
			Assert.assertEquals("Mehdi", ut1.getAssignee());
			return;
		}
		Assert.assertNotNull("nabayad code be inja miresid", null);
	}
	

}
