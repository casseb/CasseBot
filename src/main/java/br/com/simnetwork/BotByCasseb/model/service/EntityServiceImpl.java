package br.com.simnetwork.BotByCasseb.model.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.tomcat.util.collections.CaseInsensitiveKeyMap;
import org.hibernate.validator.internal.xml.ValidatedByType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.simnetwork.BotByCasseb.model.entity.dialog.structure.DialogSchema;
import br.com.simnetwork.BotByCasseb.model.entity.object.BotUser;
import br.com.simnetwork.BotByCasseb.model.entity.object.Record;
import br.com.simnetwork.BotByCasseb.model.entity.object.RecordSchema;
import br.com.simnetwork.BotByCasseb.model.entity.object.RecordStatus;
import br.com.simnetwork.BotByCasseb.model.entity.object.RecordType;
import br.com.simnetwork.BotByCasseb.model.repository.RecordRepository;
import br.com.simnetwork.BotByCasseb.model.repository.RecordSchemaRepository;

@Service("entityService")
public class EntityServiceImpl implements EntityService {

	@Autowired
	private BotUserService botUserService;
	@Autowired
	private DialogSchemaService dialogSchemaService;
	@Autowired
	private ContextService contextService;
	@Autowired
	private RecordRepository recordRepo;
	@Autowired
	private DecisionService decisionService;
	@Autowired
	private RecordSchemaRepository recordSchemaRepo;
	@Autowired
	private KeyService keyService;

	public void synchronizeStaticEntities() {

		synchronizeBotUserEntity();
		synchronizeDialogSchemaEntity();

		for (String recordSchemaName : contextService.getEntitySchemasBeanDefinitionNames(true)) {
			RecordSchema recordSchema = (RecordSchema) contextService.getObjectBean(recordSchemaName,
					RecordSchema.class);
			recordSchemaRepo.save(recordSchema);
		}

	}

	public void synchronizeStaticEntitiesTest() {
		for (String recordSchemaName : contextService.getEntitySchemasBeanDefinitionNames(false)) {
			RecordSchema recordSchema = (RecordSchema) contextService.getObjectBean(recordSchemaName,
					RecordSchema.class);
			recordSchemaRepo.save(recordSchema);
		}
	}

	public List<Record> findByKeys(String entityName, String key) {
		return recordRepo.findByEntityNameAndKey(entityName, key);
	}

	public List<Record> findByKeys(String entityName, List<String> keys) {
		return recordRepo.findByEntityNameAndKeyIn(entityName, keys);
	}

	public List<String> findByFields(String entityName, Map<String, String> decisions) {
		decisions = decisionService.getDecisionsFilter(decisions, "query:");
		List<Record> records = new LinkedList<Record>();
		if (!decisions.isEmpty()) {
			for (String key : decisions.keySet()) {
				records = internalSearch(records, entityName, key, decisions.get(key));
			}
			return keyService.parseStringKey(records);
		} else {
			return keyService.parseStringKey(recordRepo.findByEntityName(entityName));
		}

	}

	private List<Record> internalSearch(List<Record> target, String entityName, String fieldName, String value) {
		List<Record> result = new LinkedList<Record>();
		target = findByKeys(entityName, keyService.parseStringKey(target));
		for (Record record : target) {
			if (record.getFieldName().equals(fieldName) && record.getValue().contains(value)) {
				result.add(record);
			}
		}
		return result;
	}

	public void deleteByKey(String entityName, String key) {
		recordRepo.delete(findByKeys(entityName, key));
	}

	public RecordType getType(Record record, String fieldName) {
		return recordSchemaRepo.findByEntityNameAndFieldName(record.getEntityName(), fieldName).getType();
	}

	public RecordType getType(String entityName, String fieldName) {
		return recordSchemaRepo.findByEntityNameAndFieldName(entityName, fieldName).getType();
	}

	public Boolean setValue(Record record, String fieldName, String newValue) {
		if (validateValue(record, fieldName, newValue)) {
			record.setValue(newValue);
			recordRepo.save(record);
			return true;
		} else {
			return false;
		}
	}

	public Boolean validateValue(Record record, String fieldName, String newValue) {
		RecordType type = getType(record, fieldName);

		switch (type) {
		case STRING:
			return true;
		case INTEGER:
			return validateInteger(newValue);
		case BOOLEAN:
			return validateBoolean(newValue);
		case RECORD:
			return validateRecord(record, newValue);
		case BOTUSER:
			return validateBotUser(newValue);
		case DIALOGSCHEMA:
			return validateDialogSchema(newValue);
		}

		return false;

	}

	public boolean validateInteger(String value) {
		try {
			Integer.parseInt(value);
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	public boolean validateBoolean(String value) {
		return Boolean.parseBoolean(value);
	}

	public boolean validateRecord(Record record, String value) {
		return !findByKeys(record.getEntityName(), value).isEmpty();
	}

	public boolean validateBotUser(String value) {
		try {
			return botUserService.locateBotUser(Integer.parseInt(value)) != null;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean validateDialogSchema(String value) {
		try {
			return dialogSchemaService.findDialogSchemabyNomeSchema(value) != null;
		} catch (Exception e) {
			return false;
		}
	}

	public Integer getInteger(Record record) {
		if (validateInteger(record.getValue())) {
			return Integer.parseInt(record.getValue());
		}
		return null;
	}

	public Boolean getBoolean(Record record) {
		if (validateBoolean(record.getValue())) {
			return Boolean.parseBoolean(record.getValue());
		}
		return null;
	}

	public List<Record> validateRecord(Record record) {
		if(validateRecord(record,record.getValue())) {
			return findByKeys(record.getEntityName(), record.getValue());
		}
			return null;
	}

	public BotUser getBotUser(Record record) {
		if(validateBotUser(record.getValue())) {
			return botUserService.locateBotUser(Integer.parseInt(record.getValue()));
		}
			return null;
	}

	public DialogSchema getDialogSchema(Record record) {
		if(validateDialogSchema(record.getValue())) {
			return dialogSchemaService.findDialogSchemabyNomeSchema(record.getValue());
		}
			return null;
	}
	
	//<------------------

	public void synchronizeBotUserEntity() {
		recordRepo.delete(recordRepo.findByEntityName("botUser"));
		recordSchemaRepo.delete(recordSchemaRepo.findByEntityName("botUser"));

		recordSchemaRepo.save(new RecordSchema(1,"botUser","id",RecordType.INTEGER,null,true,true));
		recordSchemaRepo.save(new RecordSchema(1,"botUser","data",RecordType.BOTUSER,null,true,true));

		for (BotUser botUser : botUserService.locateAllBotUsers()) {

			Map<String, String> content = new HashMap<String, String>();
			content.putIfAbsent("id", botUser.getId().toString());

			insertRecord("botUser", content);
		}

	}

	private void synchronizeDialogSchemaEntity() {

		recordRepo.delete(recordRepo.findByEntityName("dialogSchema"));
		recordSchemaRepo.delete(recordSchemaRepo.findByEntityName("dialogSchema"));

		recordSchemaRepo.save(new RecordSchema(1,"dialogSchema","nomeSchema",RecordType.DIALOGSCHEMA,null,true,true));
		recordSchemaRepo.save(new RecordSchema(1,"dialogSchema","data",RecordType.BOTUSER,null,true,true));

		for (DialogSchema dialogSchema : dialogSchemaService.findAllDialogSchema()) {
			Map<String, String> content = new HashMap<String, String>();
			content.putIfAbsent("id", dialogSchema.getNomeSchema());
			insertRecord("dialogSchema", content);
		}

	}

	public RecordStatus insertRecord(String entityName, Map<String, String> content) {

		List<RecordSchema> recordSchemas = recordSchemaRepo.findByEntityName(entityName);
		if (recordSchemas.isEmpty()) {
			return RecordStatus.ENTIDADE_INEXISTENTE;
		}

		String key = "";

		if (content.containsKey(recordSchemaRepo.findByEntityNameAndIsKey(entityName, true))) {
			return RecordStatus.CHAVE_NULL;
		} else {
			List<RecordSchema> keys = recordSchemaRepo.findByEntityNameAndIsKey(entityName, true);
			key = content.get(keys.get(0).getFieldName());
			if(keys.size()!=1) {
				keys.remove(0);
				for(RecordSchema keySchema : keys) {
					key = key+"-"+content.get(keySchema.getFieldName());
				}
			}
		}

		for (RecordSchema fieldSchema : recordSchemaRepo.findByEntityNameAndNotNull(entityName, true)) {
			if (content.get(fieldSchema.getFieldName()) == null) {
				return RecordStatus.CAMPO_OBRIGATORIO_NULL;
			}
		}

		for (RecordSchema fieldSchema : recordSchemas) {

			String fieldValue = content.get(fieldSchema.getFieldName());
			if (fieldValue != null) {
				Record record = new Record(entityName,key,fieldSchema.getFieldName(),fieldValue);
				recordRepo.save(record);
			} else if (fieldSchema.getDefaultValue() != null) {
				Record record = new Record(entityName,key,fieldSchema.getFieldName(),fieldSchema.getDefaultValue());
				recordRepo.save(record);
			}
		}

		return RecordStatus.SUCESSO;

	}


}
