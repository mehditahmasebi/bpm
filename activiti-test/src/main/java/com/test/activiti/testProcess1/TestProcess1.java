package com.test.activiti.testProcess1;

import java.util.List;

import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.log4j.Logger;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.test.activiti.BaseTest;

//@FixMethodOrder(MethodSorters.JVM)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestProcess1 extends BaseTest {
	
	public static String processDefinitionKey = "TestProcess1"; 
	public static String createdProcessInstanceId = null;
	
	Logger logger = Logger.getLogger(TestProcess1.class);
	

	public TestProcess1() {
		System.err.println("TestProcess1 Init");
	}
	
	@Override
	protected String[] getBpmnFiles() {
		return null;
	}
	
	@Test
	public void a_createNewProcessInstance()
	{
		logger.info("Create a new process instance : ");
		ProcessInstance processInstance = processEngine.getProcessEngine().getRuntimeService().startProcessInstanceByKey(processDefinitionKey);
		logger.info("  New process instance : " + processInstance.getId());
		createdProcessInstanceId = processInstance.getId();
	}
	
	@Test
	public void b_showInvolvedTaskForKermit()
	{
		List<Task> tasks = processEngine.getProcessEngine().getTaskService().createTaskQuery().processDefinitionKey(processDefinitionKey).taskInvolvedUser("kermit").list();
		logger.info("Get involved task for kermit : ");
		for(Task task : tasks)
		{
			logger.info("  Task name : " + task.getName() + " , Assigne : " + task.getAssignee() + " TaskID : " + task.getId() + " ProcInst ID : " + task.getProcessInstanceId());
		}
	}
	@Test
	public void c_showInvolvedTaskForFozzie()
	{
		List<Task> tasks = processEngine.getProcessEngine().getTaskService().createTaskQuery().processDefinitionKey(processDefinitionKey).taskInvolvedUser("fozzie").list();
		logger.info("Get involved task for fozzie : ");
		for(Task task : tasks)
		{
			logger.info("  Task name : " + task.getName() + " , Assigne : " + task.getAssignee() + " TaskID : " + task.getId() + " ProcInst ID : " + task.getProcessInstanceId());
		}
	}
	@Test
	public void d_showAssigneeTaskForKermit()
	{
		List<Task> tasks = processEngine.getProcessEngine().getTaskService().createTaskQuery().processDefinitionKey(processDefinitionKey).taskAssignee("kermit").list();
		logger.info("Show Assignee task for kermit : ");
		for(Task task : tasks)
		{
			logger.info("  Task name : " + task.getName() + " , Assigne : " + task.getAssignee() + " TaskID : " + task.getId() + " ProcInst ID : " + task.getProcessInstanceId());
		}
	}	
	@Test 
	public void e_doTaskHuman1ByKermit()
	{
		logger.info("Do Task Human 1 By Kermit");
		Task task = processEngine.getProcessEngine().getTaskService().createTaskQuery().taskAssignee("kermit").singleResult();
		processEngine.getProcessEngine().getTaskService().complete(task.getId());
		logger.info("  Task Human 1 Completed");
		
	}
	@Test
	public void f_deleteProcessInstance()
	{
		logger.info("Try to remove Process Instance");
		if(processEngine.getProcessEngine().getRuntimeService().createProcessInstanceQuery().processInstanceId(createdProcessInstanceId).singleResult() != null)
		{
			processEngine.getProcessEngine().getRuntimeService().deleteProcessInstance(createdProcessInstanceId, "TestDeleted");
			logger.info("  Try to remove Process Instance DONE SUCCESSFULLy");
		}
		else
			logger.info("  Try to remove Process Instance -- NOTHING");
	}
	
	@Test
	public void g_showHistory()
	{
		logger.info("Show History");
		HistoricProcessInstance historicProcessInstance =  processEngine.getProcessEngine().getHistoryService().createHistoricProcessInstanceQuery().processInstanceId(createdProcessInstanceId).singleResult();
		logger.info("  Start : " + historicProcessInstance.getStartTime() + "  End time : " + historicProcessInstance.getEndTime());
		
		logger.info("  Historic Tasks : ");
		List<HistoricTaskInstance> historicTasks =  processEngine.getProcessEngine().getHistoryService().createHistoricTaskInstanceQuery().processInstanceId(createdProcessInstanceId).list();
		for(HistoricTaskInstance historicTask : historicTasks)
		{
			logger.info("    Name : " + historicTask.getName() + " , Start time : " + historicTask.getStartTime() + " , End time : " + historicTask.getEndTime() + " , Finished By : " + historicTask.getAssignee());
		}
		
		logger.info("  Historic Activity : ");

		List<HistoricActivityInstance> historicActivities =  processEngine.getProcessEngine().getHistoryService().createHistoricActivityInstanceQuery().processInstanceId(createdProcessInstanceId).list();
		for(HistoricActivityInstance historicActivity : historicActivities)
		{
			logger.info("    Name : " + historicActivity.getActivityName() + ", Activity Type : " + historicActivity.getActivityType() + " , Start time : " + historicActivity.getStartTime() + " , End time : " + historicActivity.getEndTime() + " , Finished By : " + historicActivity.getAssignee());
		}
		 
	}

}
