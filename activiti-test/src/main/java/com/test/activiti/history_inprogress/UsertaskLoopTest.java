package com.test.activiti.history_inprogress;

import org.activiti.engine.runtime.ProcessInstance;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Test;

import com.test.activiti.BaseTest;

public class UsertaskLoopTest extends BaseTest {

	public static final String KEY = "UsertaskLoop";
	Logger logger = Logger.getLogger(UsertaskLoopTest.class);
	
	@Override
	protected String[] getBpmnFiles() {
		return new String[] {
				"com/test/activiti/history_inprogress/UsertaskLoop.bpmn"	
			};
	}

	@Test
	public void test_1() {
		ProcessInstance pi = runtimeService.startProcessInstanceByKey(KEY);
		historyService.createHistoricTaskInstanceQuery().processInstanceId(pi.getId()).count();
	}
	@After
	public void after()
	{
		System.out.println("UsertaskLoopTest.after()");
		try {
			Thread wait = new Thread(new Runnable() {
				
				@Override
				public void run() {
					for(;;);
				}
			});
			wait.start();
//			wait.setDaemon(false);
			wait.join(50000);
			System.out.println("UsertaskLoopTest.after() -- End");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
