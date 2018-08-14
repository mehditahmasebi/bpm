package com.test.activiti;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


public class MyProcessEngineSuite {
	
	Logger logger = Logger.getLogger(MyProcessEngineSuite.class);
	
	@Before
	public void before()
	{
		logger.info("Prepare Infrastructure");
	}

}
