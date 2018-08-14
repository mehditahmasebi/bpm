package com.test.activiti.signalevent2;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.test.activiti.BaseTest;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
/**
 * می توان چند فرآیند با یک سیگنال مستقر کرد
 * می توان محدوده یک سیگنال را به یک فرآیند محدود یا جهانی تعریف کرد
 * از کد بیزینس نمی توان استفاده کرد اما می توان پارامتر پاس داد
 * پیام از دو متد زیر پشتیبانی می کند
 * 	runtime.startProcessInstanceByMessage
 * 	runtime.runtimeService.messageEventReceived
 * اما رخداد فقط از متند دوم پشتیبانی می کند
 * @author Mehdi
 *
 */
public class TestSignal extends BaseTest {

	@Override
	protected String[] getBpmnFiles() {
		return new String[] {
				"com/test/activiti/signalevent2/Signal.bpmn"				
		};
	}
	
	/**
	 * GLOBAL SIGNAL
	 * No Exception but nothing happened!
	 * but in message, this test thrown exception
	 */
	@Test
	public void a_sendSignalIn2Process_NotFound()
	{
		runtimeService.startProcessInstanceByKey("Signal","11111");
	}
	
	/**
	 * GLOBAL SIGNAL
	 * Successful
	 */
	@Test
	public void b_sendSignalIn2Process_Successful()
	{
		processDeployer.deploy("com/test/activiti/signalevent2/SignalCatch.bpmn");
		
		// کد بیزینس قابل ارجا نبود
		runtimeService.startProcessInstanceByKey("Signal","22222"); 
		
	}
	
	/**
	 * GLOBAL SIGNAL
	 * دو فرآیند اجرا شدند و به موازات هم کار کردند
	 */
	@Test
	public void c_sendSignalIn2Process_Duplicate()
	{
		processDeployer.deploy("com/test/activiti/signalevent2/SignalCatchDuplicate.bpmn");
		runtimeService.startProcessInstanceByKey("Signal","33333");
	}
//	
//	/**
//	 */
//	@Test
//	public void d_sendSignalIn2Process_OnlyStartSignal_NoSignalDef()
//	{
//		processDeployer.deploy("com/test/activiti/signalevent2/SignalCatchOnlyStartSignal.bpmn");
//		runtimeService.startProcessInstanceByKey("Signal","33333");
//	}

}
