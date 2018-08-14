package com.test.activiti.listener;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Map.Entry;

import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.log4j.Logger;
import org.junit.Test;

import com.test.activiti.BaseTest;

public class ListenerProcessTest2 extends BaseTest {
	public static String KEY = "ListenerProcess2";
	Logger logger = Logger.getLogger(ListenerProcessTest2.class);

	@Override
	protected String[] getBpmnFiles() {
		return new String[] {
				"com/test/activiti/listener/ListenerProcess2.bpmn"
		};
	}
	
	@Test
	public void test()
	{
		//Register Engine level TASK_CREATED Listener
		runtimeService.addEventListener(new MyActivitiEventListener(), ActivitiEventType.TASK_CREATED);

		
		
		ProcessInstance pi = runtimeService.startProcessInstanceByKey(KEY);
		logger.info("Process Started with id : " + pi.getId());
		Task ut1 = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
		assertNotNull("Bayad Assignee dashte bashad", ut1.getAssignee());
		logger.info("-- Assignee Name : " + ut1.getAssignee());
		logger.info("Want to complete UT1 Task");
		taskService.complete(ut1.getId());
		
		//Etminan az inke parameter set shodeh dar sathe process budeh na dar sathe Execution
		logger.info("Trying to show variable at process instance LEVEL");
		HistoricProcessInstance hpi = historyService.createHistoricProcessInstanceQuery().processInstanceId(pi.getId()).includeProcessVariables().singleResult();
		assertTrue("Bayad Parameter bashad!!!!", hpi.getProcessVariables().containsKey("Param2"));
		
		for(Entry<String, Object> pair : hpi.getProcessVariables().entrySet())
		{
			logger.info(" -- Key : " + pair.getKey() + " , Value : " + pair.getValue());
		}
		
		logger.info("Process has finished");
		
	}

}
