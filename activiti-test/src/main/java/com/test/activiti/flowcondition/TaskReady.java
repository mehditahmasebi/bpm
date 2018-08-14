package com.test.activiti.flowcondition;

import org.activiti.engine.delegate.DelegateExecution;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service("taskReady")
public class TaskReady {
	
	Logger logger = Logger.getLogger(TaskReady.class);
	boolean isReady = false;
	
	public boolean isReady() {
		return isReady;
	}

	public void setReady(boolean isReady) {
		this.isReady = isReady;
	}

	public TaskReady()
	{
		logger.info("TaskReady has been created");
	}
	
	public boolean isReady(String taskname,DelegateExecution exec)
	{
		logger.info("Task name :" + taskname + " for Process Def Id : " + exec.getProcessDefinitionId());
		return isReady;
	}

}
