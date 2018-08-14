package com.test.activiti.sampleProcess;

import static org.junit.Assert.assertNotNull;

import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

import com.test.activiti.BaseTest;

public class SampleProcessTest extends BaseTest {

	@Override
	protected String[] getBpmnFiles() {
		return new String[] {"com/test/activiti/sampleProcess/SampleProcess.bpmn"};
	}
	@Test
	public void sampleFlowTest()
	{
		ProcessInstance pi = super.runtimeService.startProcessInstanceByKey("process_1");
		Task ut1 = super.taskService.createTaskQuery()
										.processInstanceId(pi.getId())
										.active()
										.singleResult();
		assertNotNull(ut1);
		super.taskService.complete(ut1.getId());
		HistoricProcessInstance historicProcess = super.historyService.createHistoricProcessInstanceQuery()
																		.processInstanceId(pi.getId())
																		.finished()
																		.singleResult();
		
		assertNotNull("Process must be finished",historicProcess);
		
	}
	
}
