package com.test.activiti.timerprocess;

import java.io.IOException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Test;

import com.test.activiti.BaseTest;

public class TimerTest4  extends BaseTest{
	Logger logger = Logger.getLogger(TimerTest4.class);

	@Override
	protected String[] getBpmnFiles() {
		return new String[] {"com/test/activiti/timerprocess/Timer4.bpmn"};
	}
	
	String pid = null;

	@Test
	/**
	 * dar in mesal maazaade TimerTest3
	 * yek loop gozashtam ke az yek switch estefade mikonad
	 * ta bebinam aya jolo raftane process ra mishabad khodkar kard!?
	 * 
	 * natije:
	 * daghighan hamantor ke khastam, loop wait be dorosti kar kard, va har 2 mins yekbar sa'y kar jolo bere ama chon shart bargharar nabood
	 * mojadad bargasht be wait
	 * sepas engine o stop v result e condition o true kardam, baaz daghighan monaaseb kar kard
	 */
	public void a_test()
	{

		if(pid == null)
		{
			final ProcessInstance pi = runtimeService.startProcessInstanceByKey("Timer4");
			logger.info("Process Started with ID : " + pi.getId());
			pid = pi.getId();					
		}
		else
			logger.info("The Process instance id :  " + pid + ", based on pid is set, no new process instance started again");
		
		final long now = new Date().getTime();
		
		//Stop Engine , and put the id from logger and set pid String and then again start test
		
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				HistoricProcessInstance hpi = historyService.createHistoricProcessInstanceQuery().processInstanceId(pid).singleResult();
				logger.info("Process Instance : " + hpi.getId() + ", Time " + (new Date().getTime()-now)/1000 + " , Endtime = " + hpi.getEndTime());
			}
		}, 1000,1000);
	}
	
	
	
	@After
	public void after()
	{
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
