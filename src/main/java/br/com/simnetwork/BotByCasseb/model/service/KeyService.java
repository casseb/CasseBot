package br.com.simnetwork.BotByCasseb.model.service;

import java.util.List;

import br.com.simnetwork.BotByCasseb.model.entity.object.Record;

public interface KeyService {

	public List<String> parseStringKey(String key);
	public List<String> parseStringKey(List<Record> records);
	
}
