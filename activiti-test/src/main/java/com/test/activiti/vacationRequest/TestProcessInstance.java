package com.test.activiti.vacationRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.task.TaskDefinition;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.test.activiti.BaseTest;
import com.test.activiti.MyProcessEngine;
import com.test.activiti.MyProcessEngineSuite;
import com.test.activiti.ProcessDeployer;

import static org.junit.Assert.*;

public class TestProcessInstance extends BaseTest {
	
//	The Engine tables are:
//		� ACT_GE_PROPERTY (Activiti version)
//		� ACT_GE_BYTEARRAY (Process version)
//		� ACT_RE_DEPLOYMENT (When and how a process is deployed)
//		� ACT_RE_PROCDEF (Process definition)
//		� ACT_RU_EXECUTION (Instance of a process)
//		� ACT_RU_JOB (Job within a process)
//		� ACT_RU_TASK (Tasks within a process)
//		� ACT_RU_IDENTITYLINK (Link between Engine and Identity)
//		� ACT_RU_VARIABLE (Variables for tasks)
//		� ACT_RU_EVENT_SUBSCR (Process events)
	
	Logger logger = Logger.getLogger(TestProcessInstance.class);
	
	@Autowired(required=true)
	ProcessDeployer processDeployer;
	
	@Autowired
	MyProcessEngine processEngine;
	
	@BeforeClass
	public static void setUp() {
		System.err.println("Initializing");
	}



	@Override
	protected String[] getBpmnFiles() {
		return new String[] {"vacationRequest/VacationRequest.bpmn"};
	}
	
	public void createAProcessInstance()
	{
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("employeeName", "Kermit");
		variables.put("numberOfDays", new Integer(4));
		variables.put("vacationMotivation", "I'm really tired!");
		
		RuntimeService runtimeService = processEngine.getProcessEngine().getRuntimeService();
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("vacationRequest",variables);
		logger.info("A new process instance created with id : " + processInstance.getId() + " ActivitiID : " + processInstance.getActivityId());
	}
	String processInstanceId = "17501";
	String processDefinitionId ="vacationRequest:1:2503";
	String processDefinitionKey ="TestProcess1";
	
	public void showManagementUserTask()
	{
		
		//Load all user task
		TaskService taskService = processEngine.getProcessEngine().getTaskService();
		List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstanceId).taskCandidateGroup("management").list();//.processInstanceId("17501").list();
		String lastTaskId = null;
		for (Task task : tasks) {
			lastTaskId = task.getId();
		  logger.info("Task available: " + task.getName());
		}
		
		//Create a new task instance
//		if(lastTaskId == null)
//		{
//			createAProcessInstance();
//			tasks = taskService.createTaskQuery().taskCandidateGroup("management").list();
//			for (Task task : tasks) {
//				lastTaskId = task.getId();
//			  logger.info("Task available: " + task.getName());
//			}
//		}

		//Get Task By Id
		assertNotNull(lastTaskId);
		Task task = taskService.createTaskQuery().taskId(lastTaskId).singleResult();
		logger.info("Task again available : " + task.getName());
		assertNotNull(task.getTaskLocalVariables());
		for(String key : task.getTaskLocalVariables().keySet())
			System.out.println("Task : " + task.getName() + "   variable name : " + key);
		
		//complete task handle vacation request
		Map<String, Object> taskVariables = new HashMap<String, Object>();
		taskVariables.put("vacationApproved", "false");
		taskVariables.put("managerMotivation", "We have a tight deadline!");
		taskService.complete(task.getId(), taskVariables);
		logger.info("Task : '" + task.getName()  + "' of process instance : " + task.getProcessInstanceId() + " with Process Definition Name : '" + processEngine.getProcessEngine().getRepositoryService().getProcessDefinition(task.getProcessDefinitionId()).getName() + "'  has completed");
		
		
		//Show all task of process
		List<Task> allTasks = processEngine.getProcessEngine().getTaskService().createTaskQuery().processInstanceId(task.getProcessInstanceId()).list();
		logger.info("All open Task of process instance : " + task.getProcessInstanceId());
		for(Task t : allTasks)
		{
			logger.info("--- Task Name : " + t.getName() + " Task Definition Key : " + t.getTaskDefinitionKey());
		}
		
		
		logger.info("Process Instance Query : " +  processEngine.getProcessEngine().getRuntimeService().createProcessInstanceQuery().count());
		
	}
	
	public void showAllOpenTaskOfAProcessInstance()	
	{
		logger.info("All open Task of process instance : " + processInstanceId);
		List<Task> allTasks = processEngine.getProcessEngine().getTaskService().createTaskQuery().processInstanceId(processInstanceId).list();
		for(Task t : allTasks)
		{
			logger.info("--- Open Task Name : '" + t.getName() + "' Task Definition Key : '" + t.getTaskDefinitionKey()+"'" + " Task Owner : '"+t.getOwner()+ "' and assigne: '" + t.getAssignee() + "'" );
		}
		
	}
	
	// HistoricTaskInstance only include user task
	public void showAllFinishedTaskOfAProcessInstance()
	{
		logger.info("All Task of process instance : " + processInstanceId);
		List<HistoricTaskInstance> allTasks = processEngine.getProcessEngine().getHistoryService().createHistoricTaskInstanceQuery().processInstanceId(processInstanceId).finished().list();
		for(HistoricTaskInstance t : allTasks)
		{
			logger.info("---Finished Task Name : '" + t.getName() + "' Task Definition Key : '" + t.getTaskDefinitionKey()+"'" + " Task Owner : '"+t.getOwner()+ "' and assigne: '" + t.getAssignee() 
					+ "' Finished Date : '" + t.getEndTime()+ "' Duration : '" + t.getDurationInMillis() + "'");
		}
	}
	
	// HistoricTaskInstance only include user task
	public void showAllActivityOfAProcessInstance()
	{
		logger.info("All Activity of process instance : " + processInstanceId);
		List<HistoricActivityInstance> activities = processEngine.getProcessEngine().getHistoryService().createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).list();
		for(HistoricActivityInstance a : activities)
		{
			logger.info("---Activity Name : '" + a.getActivityName() + "' Id: '" + a.getActivityId()+"'" + " Activity ID : '"+a.getActivityId()+ "' and Activityt type: '" + a.getActivityType()
					+ "' Finished Date : '" + a.getEndTime()+ "' Duration : '" + a.getDurationInMillis() + "'");
		}
	}	
	
	public void printAllActivityOfAProcessDefinition()
	{
		ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl)processEngine.getProcessEngine().getRepositoryService()).getDeployedProcessDefinition(processDefinitionId);
		logger.info("Activity Definition of Process Definition ID : " + processDefinitionId);
		for(ActivityImpl activity : processDefinition.getActivities())
		{
			logger.info("  Activity Definition : name : '" + activity.getProperty("name") +"' and type : " + activity.getProperty("type") + "'");
		}
	}
	
	
	public void printAllUserTaskOfAProcessDefinition()
	{
		ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl)processEngine.getProcessEngine().getRepositoryService()).getDeployedProcessDefinition(processDefinitionId);
		logger.info("UserTask Definition of Process Definition ID : " + processDefinitionId);
		for(Map.Entry<String, TaskDefinition> keyValue : processDefinition.getTaskDefinitions().entrySet())
		{
			logger.info("  UserTask Definition Map : key : '" + keyValue.getKey() +"' and Task Definition Key : '" + keyValue.getValue().getKey() + "' Task Definiton Name Expression" + keyValue.getValue().getNameExpression());
		}
	}
	
	public void getProcessDefinitionIdFromKey()
	{
		logger.info("List Of Process Definition by Key : " + processDefinitionKey);		
		List<ProcessDefinition> processDefinitions = processEngine.getProcessEngine().getRepositoryService().createProcessDefinitionQuery().processDefinitionKey(processDefinitionKey).list();
		for(ProcessDefinition proc : processDefinitions)
		{
			logger.info("  Process Definition Id : " + proc.getId());
		}
		logger.info("  Latest Process Definition ID : " + processEngine.getProcessEngine().getRepositoryService().createProcessDefinitionQuery().processDefinitionKey(processDefinitionKey).latestVersion().singleResult().getId());
	}
	
	public void printAllVariableOfAProcessInstance()
	{
		//remember variable are not returned when creating a process instance, they only filled when you query on process instance with includeProcessVariables
		//MyProcessEngine.INSTANCE.getRuntimeService().createProcessInstanceQuery().variableValueEquals(""); FIND a process instance by a variables 
		//another way: getRuntimeService().getVariables(processinstanceid);
		ProcessInstance processInstance = processEngine.getProcessEngine().getRuntimeService().createProcessInstanceQuery().processInstanceId(processInstanceId).includeProcessVariables().singleResult();
		logger.info("Process Instance Variables : " + processInstance.getProcessDefinitionName() + " " + processInstance.getId());
		if(processInstance.getProcessVariables().size() == 0)
			logger.info("  Process Instance Variables = none");
		for(Entry<String, Object> keyValue : processInstance.getProcessVariables().entrySet())
		{
			logger.info("  Process Instance Variable Name : " + keyValue.getKey() + "' and value Type : '" + keyValue.getValue().getClass() +  "' Value : " + keyValue.getValue().toString());
		}
	}
	
	public void printAllVariableOfAFinishedProcessInstance()
	{
		//remember variable are not returned when creating a process instance, they only filled when you query on process instance with includeProcessVariables
		//MyProcessEngine.INSTANCE.getRuntimeService().createProcessInstanceQuery().variableValueEquals(""); FIND a process instance by a variables 
		//another way: getRuntimeService().getVariables(processinstanceid);
		List<HistoricVariableInstance> HistoricVariableInstances = processEngine.getProcessEngine()				
				.getHistoryService()
				.createHistoricVariableInstanceQuery()
				.processInstanceId(processInstanceId)
				.list();
		logger.info("Finished Process Instance Variables : " + processInstanceId);

		
		for (HistoricVariableInstance h : HistoricVariableInstances) {
			logger.info("Finished Process Instance Variable Name : " + h.getVariableName() + "' and value Type : '" + h.getVariableTypeName() +  "' Value : " + h.getValue().toString());
		}
	}
	
	@Test
	public void deleteAProcessInstance()
	{
		String deleteProcId = "47501";
		logger.info("Trying to delete a process instance : " + deleteProcId );
		processEngine.getProcessEngine().getRuntimeService().deleteProcessInstance(deleteProcId, null);
		logger.info("  deleted successfully");
	}

}
