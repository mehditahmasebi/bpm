package com.test.activiti;
import static org.junit.Assert.assertNotNull;

import javax.annotation.PostConstruct;

import org.activiti.engine.ProcessEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class MyProcessEngine {
	
	@Autowired
	private ProcessEngine processEngine;
	
	@Autowired(required=true)
	@Qualifier("initDB")
	private InitDB initDB;
	
	@PostConstruct
	public void init()
	{
//		processEngine = ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration().buildProcessEngine();
//		System.out.println("Init:Create Process Engine");
		
		//Activiti with Spring do below line automatically
//		initDB.initDatabase();
		try {
			// هر دو خط کد زیر شدنی هست
//			processEngine = ProcessEngines.getDefaultProcessEngine();
			//Activiti with Spring do below line automatically
//			setProcessEngine(ProcessEngineConfiguration.createProcessEngineConfigurationFromResourceDefault().buildProcessEngine());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void testProcessEngine()
	{
		assertNotNull(getProcessEngine());
	}


	public ProcessEngine getProcessEngine() {
		return processEngine;
	}


	public void setProcessEngine(ProcessEngine processEngine) {
		this.processEngine = processEngine;
	}


	public InitDB getInitDB() {
		return initDB;
	}


	public void setInitDB(InitDB initDB) {
		this.initDB = initDB;
	}

}
