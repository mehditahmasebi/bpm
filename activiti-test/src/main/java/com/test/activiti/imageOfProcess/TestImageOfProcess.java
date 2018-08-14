package com.test.activiti.imageOfProcess;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.el.FixedValue;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.task.TaskDefinition;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.test.activiti.BaseTest;
import com.test.activiti.MyProcessEngine;
import com.test.activiti.ProcessDeployer;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestImageOfProcess extends BaseTest {
	
	Logger logger = Logger.getLogger(TestImageOfProcess.class);
	static final String NAME = "ImageProcess";		
	
	@Autowired(required=true)
	ProcessDeployer processDeployer;
	
	@Autowired
	MyProcessEngine processEngine;
	
	@Override
	protected String[] getBpmnFiles() {
		return new String[] {
				"com/test/activiti/imageOfProcess/ImageProcess.bpmn"};
	}
	
	
	@Test
	public void a_deploy()
	{
		assertNotNull(processEngine.getProcessEngine());
		logger.info("  ImageProcess Deployed!");
		logger.info("  Version of Deployed Process : " + processEngine.getProcessEngine().getRepositoryService().createProcessDefinitionQuery().processDefinitionKey(NAME).latestVersion().singleResult().getVersion());
		logger.info("  ID of Deployed Process : " + processEngine.getProcessEngine().getRepositoryService().createProcessDefinitionQuery().processDefinitionKey(NAME).latestVersion().singleResult().getId());
	}
	
	
	@Test
	//This method is not change anything
	public void b_changeAssignee()
	{
		logger.info("Trying to change one task assignee");
//		ProcessDefinition processDefinition = MyProcessEngine.INSTANCE.getRepositoryService().createProcessDefinitionQuery().processDefinitionKey(processDefinitionKey).latestVersion().singleResult();
//		BpmnModel model = MyProcessEngine.INSTANCE.getRepositoryService().getBpmnModel(processDefinitionKey);
		String latestProcessDefinitionId = processEngine.getProcessEngine().getRepositoryService().createProcessDefinitionQuery().processDefinitionKey(NAME).latestVersion().singleResult().getId();
		
		ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl)processEngine.getProcessEngine().getRepositoryService()).getDeployedProcessDefinition(latestProcessDefinitionId);
		for(Map.Entry<String, TaskDefinition> keyValue : processDefinition.getTaskDefinitions().entrySet())
		{
			if(keyValue.getKey().equals("UT1"))
			{
				TaskDefinition taskDefinition = keyValue.getValue();
				taskDefinition.setAssigneeExpression(new FixedValue("Kermit"));
				logger.info("UT1 assignee changed");
				break;
			}
		}
	}

	@Test
	public void c_downloadPng() throws IOException
	{
		logger.info("Trying to save Process Diagram File");
		ProcessDefinition processDefinition = processEngine.getProcessEngine().getRepositoryService().createProcessDefinitionQuery().processDefinitionKey(NAME).latestVersion().singleResult();
		int version = processEngine.getProcessEngine().getRepositoryService().createProcessDefinitionQuery().processDefinitionKey(NAME).latestVersion().singleResult().getVersion();
		InputStream imageStram = processEngine.getProcessEngine().getRepositoryService().getProcessDiagram(processDefinition.getId());
		
		File png = new File("D:\\ImageProcess_"+version+".png");
		FileOutputStream pngOut = new FileOutputStream(png);
		IOUtils.copy(imageStram, pngOut);
		imageStram.close();
		pngOut.close();
		
		InputStream bpmnStream = processEngine.getProcessEngine().getRepositoryService().getProcessModel(processDefinition.getId());
		File bpmn = new File("D:\\ImageProcess_"+version+".bpmn");
		FileOutputStream bpmnOut = new FileOutputStream(bpmn);
		IOUtils.copy(bpmnStream, bpmnOut);
		bpmnStream.close();
		bpmnOut.close();
		
		logger.info("  Process Diagram File Saved Successfuly");
	}
	
	@Test
	public void d_downloadTaskPng() throws IOException
	{
		logger.info("Trying to save Process Diagram File that focus on a Task");
		
		//create an instance
		ProcessInstance processInstance = processEngine.getProcessEngine().getRuntimeService().startProcessInstanceByKey(NAME);
		
		ProcessDefinition processDefinition = processEngine.getProcessEngine().getRepositoryService().createProcessDefinitionQuery().processDefinitionKey(NAME).latestVersion().singleResult();
		
		BpmnModel model = processEngine.getProcessEngine().getRepositoryService().getBpmnModel(processDefinition.getId());
		List<String> activities = processEngine.getProcessEngine().getRuntimeService().getActiveActivityIds(processInstance.getId());
//		List<String> flows = new ArrayList<String>();
		InputStream is = (new DefaultProcessDiagramGenerator()).generateDiagram(model, "PNG", activities);
		File png = new File("D:\\ImageProcess_TASK.png");
		FileOutputStream pngOut = new FileOutputStream(png);
		IOUtils.copy(is, pngOut);
		is.close();
		pngOut.close();
		
		
	}
}
