package com.test.activiti.serviceexception;

import static org.junit.Assert.assertNull;

import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Test;

import com.test.activiti.BaseTest;

public class TestServiceException2 extends BaseTest {
	
	public static String KEY = "exception2";
	
	Logger logger = Logger.getLogger(TestServiceException2.class); 

	@Override
	protected String[] getBpmnFiles() {
		return new String[] {"com/test/activiti/serviceexception/Exception2.bpmn"};
	}

	/**
	 * باز فرآیند بازگشت به عقب می شود و انگار کلا ساخته نشده است
	 */
	@Test
	public void simpleTest()
	{
		try {
			ProcessInstance pi = runtimeService.startProcessInstanceByKey(KEY);	
			String pid = pi.getId();
			
			pi = runtimeService.createProcessInstanceQuery().processInstanceId(pid).singleResult();
			if(pi!= null)
				logger.info("Process wait open");
			else
			{
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
