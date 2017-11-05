package br.com.simnetwork.BotByCasseb.model.service;

import java.util.List;
import java.util.Map;

import br.com.simnetwork.BotByCasseb.model.entity.dialog.structure.DialogSchema;
import br.com.simnetwork.BotByCasseb.model.entity.object.BotUser;
import br.com.simnetwork.BotByCasseb.model.entity.object.Record;
import br.com.simnetwork.BotByCasseb.model.entity.object.RecordStatus;
import br.com.simnetwork.BotByCasseb.model.entity.object.RecordType;

public interface EntityService {
	
	public void synchronizeStaticEntities();
	public void synchronizeStaticEntitiesTest();
	public List<Record> findByKeys(String entityName, String key);
	public List<Record> findByKeys(String entityName, List<String> keys);
	public List<String> findByFields(String entityName, Map<String, String> decisions);
	public void deleteByKey(String entityName, String key);
	public RecordType getType(Record record, String fieldName);
	public RecordType getType(String entityName, String fieldName);
	public Boolean setValue(Record record, String fieldName, String newValue);
	public Boolean validateValue(Record record, String fieldName, String newValue);
	public boolean validateInteger(String value);
	public boolean validateBoolean(String value);
	public boolean validateRecord(Record record, String value);
	public boolean validateBotUser(String value);
	public boolean validateDialogSchema(String value);
	public Integer getInteger(Record record);
	public Boolean getBoolean(Record record);
	public List<Record> validateRecord(Record record);
	public BotUser getBotUser(Record record);
	public RecordStatus insertRecord(String entityName, Map<String, String> content);
	public void synchronizeBotUserEntity();

}
