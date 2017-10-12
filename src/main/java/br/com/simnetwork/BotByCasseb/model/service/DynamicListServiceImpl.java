package br.com.simnetwork.BotByCasseb.model.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.simnetwork.BotByCasseb.model.entity.object.DynamicList;

@Service("dynamicListService")
public class DynamicListServiceImpl implements DynamicListService{

	@Autowired
	private ContextService contextService;
	
	@Override
	public List<String> getFormattedValues(DynamicList dynamicList) {
		if(dynamicList.getBeanType().equals("D")) {
			return contextService.getDialogSchemasBeanDefinitionNames(true);
		}
		return null;
	}

	
	
}
