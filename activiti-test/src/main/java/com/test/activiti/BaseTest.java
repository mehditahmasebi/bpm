package com.test.activiti;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(BaseTest.APPCONTEXT)
//@FixMethodOrder(MethodSorters.JVM)
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public abstract class BaseTest implements ApplicationContextAware {
	
	public static final String APPCONTEXT = "/applicationContext.xml";

	Logger logger = Logger.getLogger(BaseTest.class);
	public static ApplicationContext CONTEXT;
	
	@Autowired(required=true)
	protected ProcessDeployer processDeployer;
	
	@Autowired
	protected MyProcessEngine processEngine;
	
	protected RuntimeService runtimeService;
	protected TaskService taskService;
	protected RepositoryService repositoryService;
	protected HistoryService historyService;
	
	private List<String> deploymentIds = new ArrayList<String>();
	
	@PostConstruct	
	public void init() {
		runtimeService = processEngine.getProcessEngine().getRuntimeService();
		taskService = processEngine.getProcessEngine().getTaskService();
		repositoryService = processEngine.getProcessEngine().getRepositoryService();
		historyService = processEngine.getProcessEngine().getHistoryService();
	}
	
	@Before
	public void deploy()
	{
		if(deploymentIds.size() > 0)
			for(String deploymentId : deploymentIds)
				repositoryService.deleteDeployment(deploymentId);
		deploymentIds.clear();
		
		long beforeCount = processDeployer.getDeployedCount();
		logger.info("Number of Process Deployed Before start to deploy any new thing : " + beforeCount);
		if(getBpmnFiles() != null)
			for(String bpmn : getBpmnFiles())
			{			
				if(bpmn != null)
					deploymentIds.add(processDeployer.deploy(bpmn));
			}
//		assertNotSame(beforeCount, processDeployer.getDeployedCount());
		
		logger.info(processDeployer.getDeployedCount() + " Processes Deployed Successfully");
	}
	
	protected abstract String[] getBpmnFiles();	
	
	@Override
	@Autowired
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		CONTEXT = context;
	}
	
	public void registerSingletonBean(String name, Object obj)
	{
		ConfigurableListableBeanFactory beanFactory = ((ConfigurableApplicationContext)CONTEXT).getBeanFactory();
		
		beanFactory.registerSingleton(name, obj);
	}
	public void clearSingletonBeans()
	{
		try {
			ConfigurableListableBeanFactory beanFactory = ((ConfigurableApplicationContext)CONTEXT).getBeanFactory();
			beanFactory.destroyBean(CONTEXT.getBean("stexecution1"));
		} catch (Exception e) {
		}
		
		try {
			BeanDefinitionRegistry factory = (BeanDefinitionRegistry) CONTEXT.getAutowireCapableBeanFactory();
			factory.removeBeanDefinition("stexecution1");
		} catch (Exception e) {
		}
		
		try {
			ConfigurableListableBeanFactory beanFactory = ((ConfigurableApplicationContext)CONTEXT).getBeanFactory();
			beanFactory.destroySingletons();
		} catch (Exception e) {
		}
	}
}
