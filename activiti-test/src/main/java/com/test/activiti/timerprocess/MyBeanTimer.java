package com.test.activiti.timerprocess;

import org.activiti.engine.delegate.DelegateExecution;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service("myBeanTimer")
public class MyBeanTimer {
	
	Logger logger = Logger.getLogger(MyBeanTimer.class);
	
	public MyBeanTimer()
	{
		logger.info("MyBean Timer has been created");
	}
	
	public boolean conditionwithParam(DelegateExecution exec)
	{
		boolean result = true;
		try {
			Thread.sleep(4);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		logger.info("Condition for Process Instance ID : " + exec.getProcessInstanceId() + " ,return Result : " + result);
		return result;
	}

}
