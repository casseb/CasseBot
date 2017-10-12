package br.com.simnetwork.BotByCasseb.model.service;

import java.util.List;

import br.com.simnetwork.BotByCasseb.model.entity.object.DynamicList;

public interface DynamicListService {
	
	public List<String> getFormattedValues(DynamicList dynamicList);

}
