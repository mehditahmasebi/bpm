package com.test.activiti.idgeneration;

import java.util.Random;

import org.activiti.engine.impl.cfg.IdGenerator;

public class MyIdGenerator implements IdGenerator {

	@Override
	public String getNextId() {
		String result = "10000"+new Random().nextInt();
		return result;
	}

}
