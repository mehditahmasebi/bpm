package com.test.activiti.multipleend;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.log4j.Logger;
import org.junit.Test;

import com.test.activiti.BaseTest;

public class TestMultipleEnd extends BaseTest{
	
	
	Logger logger = Logger.getLogger(TestMultipleEnd.class);
	public static String KEY = "MultipleEnd";
	
	public static String TASKUT2A = "UT2A";

	@Override
	protected String[] getBpmnFiles() {
		return new String[] {"com/test/activiti/multipleend/MultipleEnd.bpmn"};
	}
	
	@Test
	public void startAProcessInstance()
	{
		ProcessInstance pi = processEngine.getProcessEngine().getRuntimeService().startProcessInstanceByKey(KEY);
		assertNotNull(pi);
		
		List<Task> tasks = processEngine.getProcessEngine().getTaskService().createTaskQuery().processInstanceId(pi.getId()).list();
		
		assertNotNull(tasks);
		assertEquals(tasks.size(), 1);
		
		tasks.get(0).delegate("1");
		logger.info("Task Assigne (delegated) : " + tasks.get(0).getAssignee());		
		processEngine.getProcessEngine().getTaskService().complete(tasks.get(0).getId());
		
		tasks = processEngine.getProcessEngine().getTaskService().createTaskQuery().processInstanceId(pi.getId()).list();
		assertNotNull(tasks);
		assertEquals(tasks.size(), 2);
		
		for(Task task : tasks)
			logger.info("Open Task Definition Key : " + task.getTaskDefinitionKey());
		
		List<Execution> executions = processEngine.getProcessEngine().getRuntimeService().createExecutionQuery().processInstanceId(pi.getId()).list();
		logger.info("Number of process execution after parallel : " + executions.size());
		for(Execution exec : executions)
			logger.info("Execution id : " + exec.getId() + " ,Activity ID : " + exec.getActivityId() + " ,Process Instance ID : " + exec.getProcessInstanceId());
		
		Task taskUT2A = processEngine.getProcessEngine().getTaskService().createTaskQuery().processInstanceId(pi.getId()).taskDefinitionKey(TASKUT2A).singleResult();
		assertNotNull(taskUT2A);
		processEngine.getProcessEngine().getTaskService().complete(taskUT2A.getId());
		
		tasks = processEngine.getProcessEngine().getTaskService().createTaskQuery().processInstanceId(pi.getId()).list();
		assertEquals(tasks.size(), 1);
		
		pi = processEngine.getProcessEngine().getRuntimeService().createProcessInstanceQuery().processInstanceId(pi.getId()).singleResult();
		assertNotNull("After complete UT2A process must not finished",pi);
		
		processEngine.getProcessEngine().getTaskService().complete(tasks.get(0).getId());
		
		pi = processEngine.getProcessEngine().getRuntimeService().createProcessInstanceQuery().processInstanceId(pi.getId()).singleResult();
		assertNull("After complete UT2B process must finished",pi);
	}

}
