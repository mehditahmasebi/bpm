package com.test.activiti.message;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.test.activiti.BaseTest;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
/**
 * اجازه مستقر کردن دو فرآیند که نام های مشابه از پیام دارند امکان پذیر نمی باشد
 * می توان ارسال پیام را به یک نمونه فرآیند باز محدود کرد
 * می توان پارامتر ارسال کرد
 * کد بیزینس هم می توان ارسال کرد
 * پیام از دو متد زیر پشتیبانی می کند
 * 	runtime.startProcessInstanceByMessage
 * 	runtime.runtimeService.messageEventReceived
 * اما رخداد فقط از متند دوم پشتیبانی می کند
 * @author Mehdi
 *
 */
public class TestMessage extends BaseTest {

	@Override
	protected String[] getBpmnFiles() {
		return new String[] {
				"com/test/activiti/message/Message.bpmn"				
		};
	}
	
	/**
	 * Error:
	 * Cannot start process instance by message: no subscription to message with name 'startmsg' found.
	 */
	@Test
	public void a_sendMessageIn2Process_NotFound()
	{
		runtimeService.startProcessInstanceByKey("Message","11111");
	}
	
	/**
	 * Successful
	 */
	@Test
	public void b_sendMessageIn2Process_Successful()
	{
		processDeployer.deploy("com/test/activiti/message/MessageCatch.bpmn");
		runtimeService.startProcessInstanceByKey("Message","22222");
	}
	
	/**
	 * Error:
	 * Cannot deploy process definition 'com/test/activiti/message/MessageCatchDuplicate.bpmn': there already is a message event subscription for the message with name 'startmsg'.
	 */
	@Test
	public void c_sendMessageIn2Process_Duplicate()
	{
		processDeployer.deploy("com/test/activiti/message/MessageCatchDuplicate.bpmn");
		runtimeService.startProcessInstanceByKey("Message","33333");
	}
	
	/**
	 * در این حالت تعریف نام پیام را پاک کردم اما وظیفه استارت با آن پیام را گذاشتم بماند کار خوبی نبود
	 * Error:
	 * Invalid 'messageRef': no message with that id can be found in the model
	 */
	@Test
	public void d_sendMessageIn2Process_OnlyStartMessage_NoMessageDef()
	{
		processDeployer.deploy("com/test/activiti/message/MessageCatchOnlyStartMessage.bpmn");
		runtimeService.startProcessInstanceByKey("Message","33333");
	}

}
