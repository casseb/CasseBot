package br.com.simnetwork.BotByCasseb.model.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import br.com.simnetwork.BotByCasseb.model.entity.app.StaticBean;

@Service("contextService")
public class ContextServiceImpl implements ContextService {

	@Autowired
	ApplicationContext context;

	private List<String> getAllBeansDefinitionNames(){
		List<String> result = getAllOfficialBeansDefinitionNames();
		
		for(String bean : StaticBean.getTestContext().getBeanDefinitionNames()) {
			result.add(bean);
		}
		
		return result;
	}
	
	private List<String> getAllOfficialBeansDefinitionNames(){
		List<String> result = new LinkedList<String>();
		
		for (String bean : context.getBeanDefinitionNames()) {
			result.add(bean);
		}
		
		for(String bean : StaticBean.getDynamicDialogContext().getBeanDefinitionNames()) {
			result.add(bean);
		}
		
		for(String bean : StaticBean.getStaticDialogContext().getBeanDefinitionNames()) {
			result.add(bean);
		}
		
		return result;
	}
	
	private List<String> getSchemasBeanDefinitionNamesByParameter(String parameter, boolean official) {
		List<String> result = new LinkedList<String>();
		
		List<String> beans = new LinkedList<String>();
		if(official) {
			beans = getAllOfficialBeansDefinitionNames();
		}else {
			beans = getAllBeansDefinitionNames();
		}
		for (String bean : beans) {
			if (bean.contains(parameter)) {
				result.add(bean);
			}
		}

		return result;
	}

	@Override
	public List<String> getDialogSchemasBeanDefinitionNames(boolean official) {
		return getSchemasBeanDefinitionNamesByParameter("|D|",official);
	}

	@Override
	public List<String> getStepSchemasBeanDefinitionNames(boolean official) {
		return getSchemasBeanDefinitionNamesByParameter("|S|",official);
	}

	@Override
	public int getDialogSchemasBeanDefinitionCount(boolean official) {
		return getSchemasBeanDefinitionNamesByParameter("|D|",official).size();
	}

	@Override
	public int getStepSchemasBeanDefinitionCount(boolean official) {
		return getSchemasBeanDefinitionNamesByParameter("|S|",official).size();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getObjectBean(String beanName, @SuppressWarnings("rawtypes") Class clazz) {
		if(context.containsBeanDefinition(beanName)) {
			return context.getBean(beanName,clazz);
		}
		else if(StaticBean.getDynamicDialogContext().containsBeanDefinition(beanName)) {
			return StaticBean.getDynamicDialogContext().getBean(beanName,clazz);
		}
		else if(StaticBean.getStaticDialogContext().containsBeanDefinition(beanName)) {
			return StaticBean.getStaticDialogContext().getBean(beanName,clazz);
		}
		else if(StaticBean.getTestContext().containsBeanDefinition(beanName)) {
			return StaticBean.getTestContext().getBean(beanName,clazz);
		}
		else {
			return null;
		}
	}

}
