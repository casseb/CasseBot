package br.com.simnetwork.BotByCasseb.model.service;

import java.util.List;

public interface ContextService {
	
	public List<String> getDialogSchemasBeanDefinitionNames(boolean official);
	
	public List<String> getStepSchemasBeanDefinitionNames(boolean official);
	
	public int getDialogSchemasBeanDefinitionCount(boolean official);
	
	public int getStepSchemasBeanDefinitionCount(boolean official);
	
	public Object getObjectBean(String beanName, @SuppressWarnings("rawtypes") Class clazz);

}
