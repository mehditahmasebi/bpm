package com.test.activiti;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.junit.Assert.*;

import java.util.List;

@Component
public class ProcessDeployer {
	
	Logger logger = Logger.getLogger(ProcessDeployer.class);
	
	@Autowired
	MyProcessEngine processEngine ;
	
	public ProcessDeployer()
	{
		logger.info(ProcessDeployer.class.getCanonicalName().toString() + ": Created");
		System.err.println("*********************************** Started ****************************************");
		System.err.println("************************************************************************************");
	}
	
	public String deploy(String bpmn)
	{
		assertNotNull(processEngine.getProcessEngine());
		assertNotNull(bpmn);
		RepositoryService repositoryService = processEngine.getProcessEngine().getRepositoryService();
		String id = repositoryService.createDeployment().addClasspathResource(bpmn).deploy().getId();
		logger.info("Number of process deployed: " + getDeployedCount());
		return id;
	}
	public void printAllProcessDefinition()
	{
		List<ProcessDefinition> processDefinitions = processEngine.getProcessEngine().getRepositoryService().createProcessDefinitionQuery().list();
		for(ProcessDefinition proc : processDefinitions)
		{
			logger.info("Process Definition Id : " + proc.getId() + " , Process Definition Key : " + proc.getKey() + " , Process Version : " + proc.getVersion() + " , Process isSuspend? : " + proc.isSuspended());
		}
	}
	public long getDeployedCount()
	{
		return processEngine.getProcessEngine().getRepositoryService().createProcessDefinitionQuery().count();
	}

}
