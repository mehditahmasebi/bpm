package com.test.activiti.timerprocess;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.test.activiti.BaseTest;

@RunWith(Suite.class)

@Suite.SuiteClasses({
	TestProcessTimer.class
})
public class SuiteProcessTimer extends BaseTest {

	@Override
	protected String[] getBpmnFiles() {
		return null;
	}

}
