package com.test.activiti.signalevent;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.test.activiti.BaseTest;

public class TestErrorEvent2 extends BaseTest {

	Logger logger = Logger.getLogger(TestErrorEvent2.class);
	
	@Override
	protected String[] getBpmnFiles() {
		return new String[] {"com/test/activiti/signalevent/ErrorEvent2.bpmn"};
	}
	
	/**
	 * Successful
	 * به درستی لاگر خطا را دریافت کرد
	 * اما سرویس تسک من خطای 
	 * BpmnError 
	 * پرتاب کرد
	 */
	@Test
	public void a_error()
	{
		runtimeService.startProcessInstanceByKey("ErrorEvent2");		
	}

}
