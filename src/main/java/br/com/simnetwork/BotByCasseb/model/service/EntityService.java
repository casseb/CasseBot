package br.com.simnetwork.BotByCasseb.model.service;

import java.util.Map;

import br.com.simnetwork.BotByCasseb.model.entity.dialog.structure.DialogSchema;
import br.com.simnetwork.BotByCasseb.model.entity.object.BotUser;
import br.com.simnetwork.BotByCasseb.model.entity.object.Entity;
import br.com.simnetwork.BotByCasseb.model.entity.object.Record;
import br.com.simnetwork.BotByCasseb.model.entity.object.RecordStatus;

public interface EntityService {
	
	public void synchronizeStaticEntities();
	public void synchronizeStaticEntitiesTest();
	public Record findByKey(String entityName, String key);
	public Object getValue(Record record, String fieldName);
	public String getType(Record record, String fieldName);
	public String getType(String entityName, String fieldName);
	public String getEntity(Record record);
	public Boolean setValue(Record record, String fieldName, Object newValue);
	public Boolean validateValue(Record record, String fieldName, Object newValue);
	public String getString(Record record, String fieldName);
	public Integer getInteger(Record record, String fieldName);
	public DialogSchema getDialogSchema(Record record, String fieldName);
	public BotUser getBotUser(Record record, String fieldName);
	public RecordStatus insertRecord(String entityName, Map<String, Object> content);
	public void deleteByKey(String entityName, String key);
	public void synchronizeBotUserEntity();

}
