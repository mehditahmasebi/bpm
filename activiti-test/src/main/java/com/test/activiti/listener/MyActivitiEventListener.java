package com.test.activiti.listener;

import org.activiti.engine.delegate.event.ActivitiEntityEvent;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.apache.log4j.Logger;

public class MyActivitiEventListener implements ActivitiEventListener {
	
	Logger logger = Logger.getLogger(MyActivitiEventListener.class);

	@Override
	public void onEvent(ActivitiEvent event) {
		if(event.getType().equals(ActivitiEventType.TASK_CREATED))
		{
			logger.info("Type of Event : " + event.getClass().getCanonicalName());
			if(event instanceof ActivitiEntityEvent)
			{
				if(((ActivitiEntityEvent)event).getEntity() instanceof TaskEntity)
				{
					TaskEntity task = (TaskEntity)((ActivitiEntityEvent)event).getEntity();
					logger.info("Task Name : " + task.getName() + " , ID : " +  task.getId());
					task.setAssignee("Mehdi");
				}
			}
			logger.info("PID : " + event.getProcessInstanceId() + ", and try to add (Param2) ");
			event.getEngineServices().getRuntimeService().setVariable(event.getExecutionId(), "Param2", "a s.th");
			
		}
		else
			throw new RuntimeException("man in event ro sabte nam nakardam");
		
	}

	@Override
	public boolean isFailOnException() {
		//fekr konam agar in method true bargardanad process ham khata mide
		return false;
	}

}
