package com.test.activiti;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseJavaDelegate implements JavaDelegate {
	@Autowired
	MyProcessEngine processEngine;
	
	protected RuntimeService runtimeService;
	protected TaskService taskService;
	protected RepositoryService repositoryService;
	protected HistoryService historyService;
	

	
	public BaseJavaDelegate() {
		BaseTest.CONTEXT.getAutowireCapableBeanFactory().autowireBean(this);		
		
		runtimeService = processEngine.getProcessEngine().getRuntimeService();
		taskService = processEngine.getProcessEngine().getTaskService();
		repositoryService = processEngine.getProcessEngine().getRepositoryService();
		historyService = processEngine.getProcessEngine().getHistoryService();
	}

}
