package com.test.activiti.parameter;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.log4j.Logger;
import org.junit.Test;

import com.test.activiti.BaseTest;

public class TestParameter extends BaseTest {
	
	Logger logger = Logger.getLogger(TestParameter.class);
	public static String KEY = "Parameter";
	
	@Test
	public void a_test()
	{
		Map<String, Object> vars1 = new HashMap<>();
		vars1.put("BeforeAnyTask", "A");
		ProcessInstance pi = runtimeService.startProcessInstanceByKey(KEY,vars1);
			
		Execution exec = runtimeService.createExecutionQuery().processInstanceId(pi.getId()).singleResult();
		
		logger.warn("Process Instance Id : " + pi.getId() + " Execution Id : " + exec.getId());
		
		Map<String, Object> vars2 = new HashMap<>();
		vars2.put("UT1", "UT1 parameter in new collection");
		Task ut1 = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
		taskService.complete(ut1.getId(), vars2);
		
		logger.info("After UT1 complete");
		printRuntimeVariables(exec.getId());
		printHistoricVariables(exec.getId());
		
		
		Map<String, Object> vars3 = new HashMap<>();
		vars3.put("UT2", "UT2 parameter in new collection");
		vars3.put("BeforeAnyTask", "B");
		Task ut2 = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
		taskService.complete(ut2.getId(), vars3);
		
		logger.info("After UT2 complete");
		printHistoricVariables(exec.getId());
		
	}
	
	@Override
	protected String[] getBpmnFiles() {
		return new String[] {
				"com/test/activiti/parameter/Parameter.bpmn"};
	}
	
	public void printRuntimeVariables(String processInstanceId)
	{
		// -----
		logger.info("Runtime Parameter of Process : ");
		Map<String,Object> vars = runtimeService.getVariables(processInstanceId);
		for(Map.Entry<String, Object> pairs : vars.entrySet())
		{
			logger.info(" -- Parameter Name : " + pairs.getKey() + " value : " + pairs.getValue().toString());
		}
	}
	/**
	 * includeProcessVariables is important hint!!!!
	 * @param processInstanceId
	 */
	public void printHistoricVariables(String processInstanceId)
	{
		// -----
		System.out.println("Historic Parameter of Process : ");
		HistoricProcessInstance hpi = historyService.createHistoricProcessInstanceQuery().includeProcessVariables().processInstanceId(processInstanceId).singleResult();
		for(Map.Entry<String, Object> pairs : hpi.getProcessVariables().entrySet())
		{
			logger.info(" -- Parameter Name : " + pairs.getKey() + " value : " + pairs.getValue().toString());
		}
	}

}
