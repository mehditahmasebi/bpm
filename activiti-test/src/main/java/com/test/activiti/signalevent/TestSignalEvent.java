package com.test.activiti.signalevent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Date;
import java.util.List;

import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Test;

import com.test.activiti.BaseTest;

public class TestSignalEvent extends BaseTest {
	
	Logger logger = Logger.getLogger(TestSignalEvent.class);
	public static String KEY = "SignalEvent";
	public static String CATCHING_KEY = "CatchingEvent";	

	@Override
	protected String[] getBpmnFiles() {
		return new String[] {"com/test/activiti/signalevent/SignalEvent.bpmn",
							 "com/test/activiti/signalevent/CatchingEvent.bpmn",
							 "com/test/activiti/signalevent/FireSignalEvent.bpmn",
							 "com/test/activiti/signalevent/StartSignalEvent.bpmn",
							 "com/test/activiti/signalevent/signal-intermediate-catch-2.bpmn",
							 "com/test/activiti/signalevent/signal-intermediate-catch-3.bpmn"};
	}
	
	/**
	 * در این مثال یک رخداد فراخوانی می کنم
	 * سپس یک PRocess Definition
	 * دیگر این رخداد را مدیریت می کند
	 * اما در Sub Process کار نکرد
	 * همچنین مهم هست که شناسه و نام رخداد با هم یکی باشد
	 * SignalEvent.bpmn and StartSignalEvent.bpmn
	 * @throws InterruptedException 
	 */
	@Test
	public void test() throws InterruptedException
	{
		for(int i=0;i<10;i++)
		{
			ProcessInstance pi = processEngine.getProcessEngine().getRuntimeService().startProcessInstanceByKey(KEY); 
			logger.info("New Process Instance By id : " + pi.getId());
		}
		
		List<Execution> executions = processEngine.getProcessEngine().getRuntimeService().createExecutionQuery().signalEventSubscriptionName("alert").list();
		for(Execution exec : executions)
			logger.info("Signal Subscription Execution id : " + exec.getId());

		
		Thread.sleep(10000);
	}
	
	/**
	 * در این مثلا نشان داده می شود که با در نظر گرفتن یک تایمر
	 * چگونه یک سیگنال در دل یک فرآیند کار میکند
	 * اما مسئله زمان تقریبی وجود داشت که باید همیشه مورد توجه باشد
	 * @throws InterruptedException
	 */
	@Test
	public void test2() throws InterruptedException
	{
		ProcessInstance pi = runtimeService.startProcessInstanceByKey("signal-intermediate-catch-2");
		
		Task task1 = processEngine.getProcessEngine().getTaskService().createTaskQuery().processInstanceId(pi.getId()).singleResult();
		taskService.complete(task1.getId());
		
		Task task2 = processEngine.getProcessEngine().getTaskService().createTaskQuery().processInstanceId(pi.getId()).singleResult();
		assertNull("Task2 not started yet",task2);
		
		List<Execution> executions = processEngine.getProcessEngine().getRuntimeService().createExecutionQuery().signalEventSubscriptionName("def").list();
		for(Execution exec : executions)
			logger.info("Signal Subscription Execution id : " + exec.getId());
		
		logger.info("Now");
		long now = new Date().getTime();
		
		Thread.sleep(10*1000); //Not Exactly**************
		
		logger.info("Now after : "+ (new Date().getTime() - now)/1000);
		now = new Date().getTime();

		List<Task> tasks = taskService.createTaskQuery().processInstanceId(pi.getId()).list();
		assertEquals("after 5s two task must be ready",2, tasks.size());
		
		logger.info("Now at the end : "+ (new Date().getTime() - now)/1000);
	}

	/**
	 * در این مثال با توجه به این که یک مسیر به پایان رسید
	 * و حتا سیگنال مسیر دیگر فراخوانی هم نشد 
	 * فرآیند بسته نشد اما هیچ وظیفه ی بازی هم دیگر ندارد
	 * این روش می تواند خطرناک باشد
	 * یا شاید هم بهتر باشد که در انتهای مسیر اصلی فرآِند حتما از خاتمه اجباری استفاده کنیم یا رخداد خاتمه
	 * @throws InterruptedException
	 */
	@Test
	public void test3() throws InterruptedException
	{
		ProcessInstance pi = runtimeService.startProcessInstanceByKey("signal-intermediate-catch-3");
		
		Task task1 = processEngine.getProcessEngine().getTaskService().createTaskQuery().processInstanceId(pi.getId()).singleResult();
		taskService.complete(task1.getId());
		
		Task task3 = processEngine.getProcessEngine().getTaskService().createTaskQuery().processInstanceId(pi.getId()).taskName("usertask3").singleResult();
		assertNotNull("Task3 must not be null",task3);
		
		Task task2 = processEngine.getProcessEngine().getTaskService().createTaskQuery().processInstanceId(pi.getId()).taskName("usertask2").singleResult();
		assertNull("Task2 not started yet",task2);
		
		taskService.complete(task3.getId());
		
		List<Task> tasks = taskService.createTaskQuery().processInstanceId(pi.getId()).list();
		assertEquals(0, tasks.size());
		
		pi = runtimeService.createProcessInstanceQuery().processInstanceId(pi.getId()).singleResult();
		logger.info("PI Status is ended? : " + pi.isEnded());
		assertNull("Process must finished but not!! so we should use terminate instead of end" , pi);
		
	}
	
	/**
	 * در این مثال که مشابه تست 3 بود سعی کردم که سیگنال دستی فراخوانی کنم
	 * اما چون قبلا یک مسیر به رخداد خاتمه رسید 
	 * دیگر سیگنال کاری نکرد
	 * و حتا باعث ایجاد وظیفه هم نشد
	 * و خوب متعاقبا فرآیند هم بسته نشد
	 * پس می تواند خطرناک باشد
	 * @throws InterruptedException
	 */
	@Test
	public void test4() throws InterruptedException
	{
		ProcessInstance pi = runtimeService.startProcessInstanceByKey("signal-intermediate-catch-3");
		
		Task task1 = processEngine.getProcessEngine().getTaskService().createTaskQuery().processInstanceId(pi.getId()).singleResult();
		taskService.complete(task1.getId());
		
		Task task3 = processEngine.getProcessEngine().getTaskService().createTaskQuery().processInstanceId(pi.getId()).taskName("usertask3").singleResult();
		assertNotNull("Task3 must not be null",task3);
		
		Task task2 = processEngine.getProcessEngine().getTaskService().createTaskQuery().processInstanceId(pi.getId()).taskName("usertask2").singleResult();
		assertNull("Task2 not started yet",task2);
		
		taskService.complete(task3.getId());
		
		List<Task> tasks = taskService.createTaskQuery().processInstanceId(pi.getId()).list();
		assertEquals(0, tasks.size());
		
		// like test3 but....
		
		List<Execution> executions = processEngine.getProcessEngine().getRuntimeService().
										createExecutionQuery()
										.processInstanceId(pi.getId())
										.signalEventSubscriptionName("def").list();
		for(Execution exec : executions)
		{
			logger.info("Send Signal for execution : " + exec.getId());
			runtimeService.signalEventReceived("def",exec.getId());	
		}
		Thread.sleep(2000);
		tasks = taskService.createTaskQuery().processInstanceId(pi.getId()).list();
		assertNotNull(runtimeService.createProcessInstanceQuery().processInstanceId(pi.getId()));
		assertEquals(1, tasks.size());
		
	}
	
	/**
	 * در این مثال نسبت به آزمون 4 تلاش کردم
	 * قبل از انجام وظیفه مسیر اصلی و عملا قبل از رخداد خاتمه
	 * سیگنال را فراخوانی کنم
	 * در این مثال سیگنال به درستی صادر شد و وظیفه آن هم به درستی ایجاد شد
	 * @throws InterruptedException
	 */
	
	@Test
	public void test5() throws InterruptedException
	{
		ProcessInstance pi = runtimeService.startProcessInstanceByKey("signal-intermediate-catch-3");
		
		Task task1 = processEngine.getProcessEngine().getTaskService().createTaskQuery().processInstanceId(pi.getId()).singleResult();
		taskService.complete(task1.getId());
		
		Task task3 = processEngine.getProcessEngine().getTaskService().createTaskQuery().processInstanceId(pi.getId()).taskName("usertask3").singleResult();
		assertNotNull("Task3 must not be null",task3);
		
		Task task2 = processEngine.getProcessEngine().getTaskService().createTaskQuery().processInstanceId(pi.getId()).taskName("usertask2").singleResult();
		assertNull("Task2 not started yet",task2);

		List<Execution> executions = processEngine.getProcessEngine().getRuntimeService().
				createExecutionQuery()
				.processInstanceId(pi.getId())
				.signalEventSubscriptionName("def").list();
		for(Execution exec : executions)
		{
			logger.info("Send Signal for execution : " + exec.getId());
			runtimeService.signalEventReceived("def",exec.getId());	
		}
		runtimeService.signalEventReceived("def");	
		
		Thread.sleep(10000);
		
		List<Task> tasks = taskService.createTaskQuery().processInstanceId(pi.getId()).list();
		assertEquals(1, tasks.size());
		
		taskService.complete(task3.getId());
	}
	
	@Test
	public void test_StartSignalEvent_Programmatically()
	{
		runtimeService.signalEventReceived("def");
	}
	
	@Test
	public void test_StartSignalEvent_By_AnotherProcess()
	{
		runtimeService.startProcessInstanceByKey("FireSignalEvent");
	}
	
	@After
	public void after()
	{
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("Test is finished");
	}
}
