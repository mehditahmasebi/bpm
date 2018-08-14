package com.test.activiti;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.apache.log4j.Logger;


public class ServiceTaskMock implements JavaDelegate {
	Logger logger = Logger.getLogger(ServiceTaskMock.class);

	@Override
	public void execute(DelegateExecution execution) throws Exception {		
		logger.info("In Service Task : Exec ID:" +execution.getId());
		logger.info("  Process Definition Name : " + execution.getEngineServices().getRepositoryService().createProcessDefinitionQuery().processDefinitionId(execution.getProcessDefinitionId()).singleResult().getName()
					+ "  Current Activity Name : " + execution.getCurrentActivityName()
					+ "  Current Activity Id : " + execution.getCurrentActivityId()
				);
		
	}

}
