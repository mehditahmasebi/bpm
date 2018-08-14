package com.test.activiti.flowcondition;

import org.activiti.engine.delegate.DelegateExecution;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service("myBean")
public class MyBean {
	
	Logger logger = Logger.getLogger(MyBean.class);
	
	public MyBean()
	{
		logger.info("MyBean has been created");
	}
	
	public boolean condition()
	{
		return false;
	}
	public boolean conditionwithParam(DelegateExecution exec)
	{
		logger.info("Condition for Process Instance ID : " + exec.getProcessInstanceId());
		return true;
	}

}
