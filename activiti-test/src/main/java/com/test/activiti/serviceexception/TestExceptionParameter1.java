package com.test.activiti.serviceexception;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import com.test.activiti.BaseTest;

public class TestExceptionParameter1 extends BaseTest {

	public static final String KEY = "ExceptionParamtere1";
	
	Logger logger = Logger.getLogger(TestExceptionParameter1.class);
	
	@Override
	protected String[] getBpmnFiles() {
		return new String[] {"com/test/activiti/serviceexception/ExceptionParamtere1.bpmn"};
	}
	
	@Test
	public void fail()
	{
		clearSingletonBeans();
		
		ExceptionParameter1ServiceTask1 st= new ExceptionParameter1ServiceTask1();
		registerSingletonBean("ExceptionParameter1ServiceTask1", st);
		
		
		ProcessInstance pi = runtimeService.startProcessInstanceByKey(KEY);
		logger.info("New Process Instance ID : " + pi.getId());
		Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
		try {
			taskService.complete(task.getId());
			
		} catch (Exception e) {
			logger.error(e);
		}		
		// NULLLLLLL
		Assert.assertNull(runtimeService.getVariable(pi.getId(), "VAR"));
	}
	
	@Test
	public void success()
	{
		clearSingletonBeans();
		
		ExceptionParameter1ServiceTask2 st= new ExceptionParameter1ServiceTask2();
		registerSingletonBean("ExceptionParameter1ServiceTask1", st);
		
		
		ProcessInstance pi = runtimeService.startProcessInstanceByKey(KEY);
		runtimeService.setVariable(pi.getProcessInstanceId(), "FOO", "BAR");
		logger.info("New Process Instance ID : " + pi.getId());
		Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
		try {
			taskService.complete(task.getId());
			
		} catch (Exception e) {
			logger.error(e);
		}		
		// NOT NULLLLLLLLLL
		Assert.assertNotNull(runtimeService.getVariable(pi.getId(), "VAR"));
	}

}
