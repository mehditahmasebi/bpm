package com.test.activiti.timerprocess;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.runtime.ProcessInstance;
import org.apache.log4j.Logger;
import org.junit.Test;

import com.test.activiti.BaseTest;

public class TimerTest2 extends BaseTest {
	
	public static final String KEY = "Timer2";
	Logger logger = Logger.getLogger(TimerTest2.class);

	@Override
	protected String[] getBpmnFiles() {
		return new String[] {"com/test/activiti/timerprocess/Timer2.bpmn"};
	}
	
	@Test
	public void a_test() throws InterruptedException
	{
		Map<String, Object> vars = new HashMap<>();
		vars.put("timerduration", "PT5S");
		ProcessInstance pi = runtimeService.startProcessInstanceByKey(KEY, vars);
		Thread.sleep(15000);
		//Work fine!
		assertEquals(null, runtimeService.createProcessInstanceQuery().processInstanceId(pi.getId()).singleResult());
	}

}
