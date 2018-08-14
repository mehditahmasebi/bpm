package com.test.activiti.execution;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

import com.test.activiti.BaseTest;

public class ExecutionTest1 extends BaseTest {
	
	public static final String KEY = "Execution1";

	@Override
	protected String[] getBpmnFiles() {
		return new String[] {"com/test/activiti/execution/Execution1.bpmn"};
	}
	
	@Test
	public void fromRuntime()
	{
		clearSingletonBeans();
		registerSingletonBean("stexecution1", new ServiceTaskExecutionFromRuntime1());	
		executeProcess();
	}
	
	@Test
	public void fromHistoric()
	{
		clearSingletonBeans();
		registerSingletonBean("stexecution1", new ServiceTaskExecutionFromHistoric1());
		executeProcess();
	}
	
	@Test
	public void fromExectuion()
	{
		clearSingletonBeans();
		registerSingletonBean("stexecution1", new ServiceTaskExecution1());
		executeProcess();
	}
	
	
	private void executeProcess()
	{
		ProcessInstance pi = runtimeService.startProcessInstanceByKey(KEY);
		Task ut1 = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
		Map<String, Object> vars = new HashMap<>();
		vars.put("var1", "yes it is available");
		taskService.complete(ut1.getId(),vars);
	}

}
