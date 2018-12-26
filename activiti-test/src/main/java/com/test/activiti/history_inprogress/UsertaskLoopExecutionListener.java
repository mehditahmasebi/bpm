package com.test.activiti.history_inprogress;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

public class UsertaskLoopExecutionListener implements ExecutionListener {

	private static final long serialVersionUID = 1L;

	@Override
	public void notify(DelegateExecution execution) throws Exception {
        System.out.println("ACTIVITY_STARTED");
        System.out.println("Task history count : " + execution.getEngineServices().getHistoryService().createHistoricTaskInstanceQuery().processInstanceId(execution.getProcessInstanceId()).count());	        
	}

}
