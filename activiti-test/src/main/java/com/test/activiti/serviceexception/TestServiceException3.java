package com.test.activiti.serviceexception;

import static org.junit.Assert.assertNull;

import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Test;

import com.test.activiti.BaseTest;

public class TestServiceException3 extends BaseTest {
	
	public static String KEY = "exception3";
	
	Logger logger = Logger.getLogger(TestServiceException3.class); 

	@Override
	protected String[] getBpmnFiles() {
		return new String[] {"com/test/activiti/serviceexception/Exception3.bpmn"};
	}

	/**
	 * natije inke user task 1 tamam nemishavad va hengame complete khata migirad
	 * albate service task 1 ejra mishavad ama service task 2 be hengame ejra dochare moshkel mishavad v amalan be'es mishe user task 1 baz bemanad 
	 */
	@Test
	public void simpleTest()
	{
		try {
			ProcessInstance pi = runtimeService.startProcessInstanceByKey(KEY);	
			String pid = pi.getId();
			
			
			//done user task 1
			Task ut1 = taskService.createTaskQuery().processInstanceId(pid).singleResult();

			//if task assigned before, claim throw exception 
			taskService.claim(ut1.getId(), "Mehdi");
			taskService.complete(ut1.getId());

			//Check State
			pi = runtimeService.createProcessInstanceQuery().processInstanceId(pid).singleResult();
			if(pi!= null)
				logger.info("Process wait open");
			else
			{
				//dar khate payin eshtebah kardam, ebteda fekr mikardam agar ye process history dashte bashe yani tamam shode ama:
				// az haman ebteda be ezaye process ye histori dorost mishe
				HistoricProcessInstance hpi = historyService.createHistoricProcessInstanceQuery().processInstanceId(pid).singleResult();
				if(hpi != null)
					logger.info("Process is finished : PID : " + pid + "  Historic ID : " + hpi.getId());
				else
					logger.info("Process is not at runtime and historic!!");
				assertNull("App End Successfully but Why???",hpi);
			}
		} catch (Exception e) {
			logger.error(e,e);
		}
		
		
	}
	
	@After
	public void after()
	{
		try {
			Thread wait = new Thread(new Runnable() {
				
				@Override
				public void run() {
					for(;;);
				}
			});
			wait.setDaemon(false);
			wait.join(50000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
