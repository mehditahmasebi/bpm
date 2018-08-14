package com.test.activiti.signalevent;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.test.activiti.BaseTest;

public class TestErrorEvent3 extends BaseTest {

	Logger logger = Logger.getLogger(TestErrorEvent3.class);
	
	@Override
	protected String[] getBpmnFiles() {
		return new String[] {"com/test/activiti/signalevent/ErrorEvent3.bpmn"};
	}
	
	/**
	 * Exception
	 * خطا پرتاب شد
	 * اما فرآیند مدیریت خطا آن را دریافت نکرد و فرآیند به انتها نرسید
	 */
	@Test
	public void a_error()
	{
		runtimeService.startProcessInstanceByKey("ErrorEvent3");		
	}

}
