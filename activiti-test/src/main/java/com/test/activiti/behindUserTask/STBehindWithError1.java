package com.test.activiti.behindUserTask;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.activiti.engine.task.Task;
import org.apache.log4j.Logger;
import org.junit.Assert;

public class STBehindWithError1 implements JavaDelegate {
	
	Logger logger = Logger.getLogger(STBehindWithError1.class);

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		Task behindTask = execution.getEngineServices().getTaskService().createTaskQuery()
				.executionId(execution.getId()).singleResult();
		Assert.assertNotNull(behindTask.getAssignee());
		
		
		//Change Assignee
		execution.getEngineServices().getTaskService().setAssignee(behindTask.getId(), "Ali");
		Task behindTaskAgain = execution.getEngineServices().getTaskService().createTaskQuery()
				.executionId(execution.getId()).singleResult();
		
		logger.info("Last User Task Assignee : " + behindTaskAgain.getAssignee());
		
		
		throw new RuntimeException("throw exception for monitor last user task assignee");
	}

}
