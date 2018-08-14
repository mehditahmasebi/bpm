package com.test.activiti.sampleProcess;
import org.activiti.engine.RepositoryService;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.test.activiti.MyProcessEngine;

import static org.junit.Assert.*;


public class TestDeployProcess {
	
	Logger logger = Logger.getLogger(TestDeployProcess.class);
	
	MyProcessEngine processEngine ;
	
	@Before
	public void init()
	{
		processEngine = new MyProcessEngine();
	}
	
	@Test
	public void deploy()
	{
		assertNotNull(processEngine.getProcessEngine());
		RepositoryService repositoryService = processEngine.getProcessEngine().getRepositoryService();
		repositoryService.createDeployment().addClasspathResource("sampleProcess/SampleProcess.bpmn").deploy();
		logger.info("Number of process definitions: " + repositoryService.createProcessDefinitionQuery().count());
	}

}
