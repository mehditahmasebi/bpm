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

public class TimerTest3  extends BaseTest{
	Logger logger = Logger.getLogger(TimerTest3.class);

	@Override
	protected String[] getBpmnFiles() {
		return new String[] {"com/test/activiti/timerprocess/Timer3.bpmn"};
	}
	
	String pid = null;

	@Test
	/**
	 * baraye avalin bar pid = null gozashtam start kardam, ta logg zad ke pi satkhe shodeh stop kardam, pid ro copy kardam va ba gozasht chand saanie start kardam
	 * vaghti start kardam, ba'd az gozasht modati farayad jolo raft o tamam shod
	 * agar masalan phase aval karam 1 min tool keshideh bashe v timer task 2 min wait konad, vaghti 2 bare engine ro start kardam zarfe 1 min process finish shod, na inke az aval 2 min tool bekesheh
	 * 
	 * hala mikhaham phase 1 ro ejra konam, engine o stop konam, ba'd az 2 mins ke forje wait tamam shodeh bashad start konam bebinam chi misheh,
	 * 
	 * vaghti ba takhir e bish az 2 mins engine o start kardam avalin karesh in bud k process e ghabli baaz o tamam koneh 
	 */
	public void a_test()
	{

		if(pid == null)
		{
			final ProcessInstance pi = runtimeService.startProcessInstanceByKey("Timer3");
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
