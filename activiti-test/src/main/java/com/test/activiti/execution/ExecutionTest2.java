package com.test.activiti.execution;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ConfigurableApplicationContext;

import com.test.activiti.BaseTest;

public class ExecutionTest2 extends BaseTest {
	
	public static final String KEY = "Execution2";

	@Override
	protected String[] getBpmnFiles() {
		return new String[] {"com/test/activiti/execution/Execution2.bpmn"};
	}
	
	@Test
	public void fromRuntime()
	{
		ConfigurableListableBeanFactory beanFactory = ((ConfigurableApplicationContext)CONTEXT).getBeanFactory();
		clearServiceTaskBean();
		beanFactory.registerSingleton("stexecution1", new ServiceTaskExecutionFromRuntime1());	
		executeProcess();
	}
	
	@Test
	public void fromHistoric()
	{
		ConfigurableListableBeanFactory beanFactory = ((ConfigurableApplicationContext)CONTEXT).getBeanFactory();
		clearServiceTaskBean();
		beanFactory.registerSingleton("stexecution1", new ServiceTaskExecutionFromHistoric1());
		executeProcess();
	}
	
	@Test
	public void fromExectuion()
	{
		ConfigurableListableBeanFactory beanFactory = ((ConfigurableApplicationContext)CONTEXT).getBeanFactory();
		clearServiceTaskBean();
		beanFactory.registerSingleton("stexecution1", new ServiceTaskExecution1());
		executeProcess();
	}
	
	private void clearServiceTaskBean()
	{
		try {
			ConfigurableListableBeanFactory beanFactory = ((ConfigurableApplicationContext)CONTEXT).getBeanFactory();
			beanFactory.destroyBean(CONTEXT.getBean("stexecution1"));
		} catch (Exception e) {
		}
		
		try {
			BeanDefinitionRegistry factory = (BeanDefinitionRegistry) CONTEXT.getAutowireCapableBeanFactory();
			factory.removeBeanDefinition("stexecution1");
		} catch (Exception e) {
		}
		
		try {
			ConfigurableListableBeanFactory beanFactory = ((ConfigurableApplicationContext)CONTEXT).getBeanFactory();
			beanFactory.destroySingletons();
		} catch (Exception e) {
		}
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
