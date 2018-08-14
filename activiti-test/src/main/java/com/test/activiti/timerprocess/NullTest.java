package com.test.activiti.timerprocess;

import java.io.IOException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.test.activiti.BaseTest;

public class NullTest extends BaseTest {
	
	Logger logger = Logger.getLogger(NullTest.class);

	@Override
	protected String[] getBpmnFiles() {
		return null;
	}
	
	@Test
	public void onlyWait()
	{
		final long now = new Date().getTime();
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				logger.info("Time " + (new Date().getTime()-now)/1000);
			}
		}, 1000,1000);
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
