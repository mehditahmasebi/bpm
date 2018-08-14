package com.test.activiti.tic.changeport;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.test.activiti.BaseTest;

public class ChangePortTest extends BaseTest {
	
	Logger logger = Logger.getLogger(ChangePortTest.class);
	
	@Autowired
	ChangePortTaskReady changePortTaskReady; 
	
	@Autowired
	STNewContract stNewContract;
	
	@Autowired
	STRemoveOldContract stRemoveOldContract;

	@Override
	protected String[] getBpmnFiles() {
		return new String[] {"com/test/activiti/tic/changeport/ChangePort.bpmn"};
	}
		
	
	@Test
	public void success_test() throws InterruptedException
	{
		logger.info("**** Success Scenario Test ****** ");
		changePortTaskReady.setConditionResult(false);
		stRemoveOldContract.setThrowError(false);
		stNewContract.setThrowError(false);
		
		ProcessInstance pi = runtimeService.startProcessInstanceByKey("ChangePort");
		logger.info("Process Started with id : " + pi.getId());
		
		Thread.sleep(120000); // ta process yekbar halate false e wait ra test konad
		HistoricProcessInstance hpi = historyService.createHistoricProcessInstanceQuery().processInstanceId(pi.getId()).singleResult();
		assertNull("Process Must be open", hpi.getEndTime());
		
		changePortTaskReady.setConditionResult(true);
		
		Thread.sleep(75000); //ta process yekbar mojadad wait ra call konad
		
		hpi = historyService.createHistoricProcessInstanceQuery().processInstanceId(pi.getId()).singleResult();
		assertNotNull("Process Must be Ended", hpi.getEndTime());
		
	}
	
	@Test
	public void error_test() throws InterruptedException
	{
		logger.info("**** Error Scenario Test ****** ");
		changePortTaskReady.setConditionResult(true); //Changed
		stRemoveOldContract.setThrowError(true);  // Changed
		stNewContract.setThrowError(false);
		
		ProcessInstance pi = runtimeService.startProcessInstanceByKey("ChangePort");
		logger.info("Process Started with id : " + pi.getId());
		
		Thread.sleep(75000); //ta process yekbar mojadad wait ra call konad
		
		Task utProblemHandling = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
		assertNotNull("Task must be opened, but not found", utProblemHandling);
		taskService.complete(utProblemHandling.getId());
		logger.info("User Task Problem Handling has been solved Successfully");
		
		HistoricProcessInstance hpi = historyService.createHistoricProcessInstanceQuery().processInstanceId(pi.getId()).singleResult();
		assertNotNull("Process Must be Ended", hpi.getEndTime());
		
	}

}
