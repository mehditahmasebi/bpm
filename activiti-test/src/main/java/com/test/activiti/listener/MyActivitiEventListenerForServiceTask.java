package com.test.activiti.listener;

import org.activiti.engine.delegate.event.ActivitiActivityEvent;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;

public class MyActivitiEventListenerForServiceTask implements ActivitiEventListener {

	@Override
	public void onEvent(ActivitiEvent event) {
	    switch (event.getType()) {

	      case JOB_EXECUTION_SUCCESS:
	        System.out.println("A job well done!");
	        break;

	      case JOB_EXECUTION_FAILURE:
	        System.out.println("A job has failed...");
	        break;

	      case ACTIVITY_COMPLETED:
	    	  if(event instanceof ActivitiActivityEvent)
	    	  {
	    		  ActivitiActivityEvent activityEvent = (ActivitiActivityEvent)event;
	    		  if(activityEvent.getActivityType().equalsIgnoreCase("servicetask"))
	    			  System.out.println("Service Task : " + activityEvent.getActivityId() + " --> Completed");
	    		  else
		    		  System.out.println("A ACTIVITY_COMPLETED has complete... ");
	    	  }
	    	  else
	    		  System.out.println("A ACTIVITY_COMPLETED has complete... ");
	        break;
	        
	      default:
	        System.out.println("Event received: " + event.getType());
	    }
	}

	@Override
	public boolean isFailOnException() {
		// TODO Auto-generated method stub
		return false;
	}

}
