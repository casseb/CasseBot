package br.com.simnetwork.BotByCasseb.model.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service("decisionService")
public class DecisionServiceImpl implements DecisionService{
	
	public Map<String,String> getDecisionsFilter(Map<String,String> decisions, String filter){
		Map<String,String> result = new HashMap<String,String>();
		for(String key : decisions.keySet()) {
			if(key.contains(filter)) {
				result.put(key.substring(filter.length()), decisions.get(key));
				result.put("unico", decisions.get(key));
			}
		}
		return result;
	}
	

}
