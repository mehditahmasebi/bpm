package com.test.activiti.history_inprogress;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.log4j.Logger;
import org.junit.Test;

import com.test.activiti.BaseTest;

public class History_InProgressTest extends BaseTest {
	
	public static final String KEY = "History_InProgress";
	Logger logger = Logger.getLogger(History_InProgressTest.class);

	@Override
	protected String[] getBpmnFiles() {
		return new String[] {
			"com/test/activiti/history_inprogress/History_InProgress.bpmn"	
		};
	}
	
	@Test
	public void test()
	{
		ProcessInstance pi = runtimeService.startProcessInstanceByKey(KEY);
		String piId = pi.getId();
		
		logger.info("Process instance id : " + piId);
		
		Task ut1 = taskService.createTaskQuery().processInstanceId(piId).singleResult();
		//LocalVariable ba'es mishe Test fail beshe, chon Conditional Flow nemitavanad expression UT1_Progress o peyda koneh
//		taskService.setVariableLocal(ut1.getId(), "UT1_InProgress", "true");
		taskService.setVariable(ut1.getId(), "UT1_InProgress", "true");
		taskService.complete(ut1.getId());
		
		Task ut1_2 = taskService.createTaskQuery().processInstanceId(piId).singleResult();
		assertNotNull("Nabayad null bashad", ut1_2);
		taskService.setVariable(ut1_2.getId(), "UT1_InProgress", "false");
		taskService.complete(ut1_2.getId());
		
		HistoricProcessInstance hpi = historyService.createHistoricProcessInstanceQuery().processInstanceId(piId).finished().singleResult();
		assertNotNull("Process mibayest finish mishod !!!!", hpi);
	
		List<HistoricTaskInstance> historicTaskInstances = historyService.createHistoricTaskInstanceQuery().processInstanceId(piId).includeProcessVariables().list();
		
		for(HistoricTaskInstance ht : historicTaskInstances)
		{
			logger.info("HistoricTask --> TaskDefKey : " + ht.getTaskDefinitionKey() +  " ,End Time : " + ht.getEndTime() + " , LocalParamteres : Size=" + ht.getTaskLocalVariables().size());
			for(Map.Entry<String, Object> param :  ht.getProcessVariables().entrySet())
			{
				logger.info("    Param Key : " + param.getKey() + " , Value : " + param.getValue());
			}
		}
		
	}
	
	

}
