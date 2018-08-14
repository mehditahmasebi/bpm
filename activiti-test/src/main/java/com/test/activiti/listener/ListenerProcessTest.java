package com.test.activiti.listener;

import static org.junit.Assert.assertTrue;

import java.util.Map.Entry;

import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.log4j.Logger;
import org.junit.Test;

import com.test.activiti.BaseTest;

public class ListenerProcessTest extends BaseTest {
	public static String KEY = "ListenerProcess";
	Logger logger = Logger.getLogger(ListenerProcessTest.class);

	@Override
	protected String[] getBpmnFiles() {
		return new String[] {
				"com/test/activiti/listener/ListenerProcess.bpmn"
		};
	}
	
	@Test
	public void test()
	{
		ProcessInstance pi = runtimeService.startProcessInstanceByKey(KEY);
		logger.info("Process Started with id : " + pi.getId());
		Task ut1 = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
		taskService.complete(ut1.getId());
		
		//Etminan az inke parameter set shodeh dar sathe process budeh na dar sathe Execution
		logger.info("Trying to show variable at process instance LEVEL");
		HistoricProcessInstance hpi = historyService.createHistoricProcessInstanceQuery().processInstanceId(pi.getId()).includeProcessVariables().singleResult();
		assertTrue("Bayad Parameter bashad!!!!", hpi.getProcessVariables().containsKey("Param1"));
		
		for(Entry<String, Object> pair : hpi.getProcessVariables().entrySet())
		{
			logger.info(" -- Key : " + pair.getKey() + " , Value : " + pair.getValue());
		}
		
		logger.info("Process has finished");
		
	}

}
