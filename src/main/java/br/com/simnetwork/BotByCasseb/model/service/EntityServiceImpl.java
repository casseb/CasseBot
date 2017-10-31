package br.com.simnetwork.BotByCasseb.model.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.simnetwork.BotByCasseb.model.entity.dialog.structure.DialogSchema;
import br.com.simnetwork.BotByCasseb.model.entity.object.BotUser;
import br.com.simnetwork.BotByCasseb.model.entity.object.Entity;
import br.com.simnetwork.BotByCasseb.model.entity.object.EntitySchema;
import br.com.simnetwork.BotByCasseb.model.entity.object.Field;
import br.com.simnetwork.BotByCasseb.model.entity.object.FieldSchema;
import br.com.simnetwork.BotByCasseb.model.entity.object.Record;
import br.com.simnetwork.BotByCasseb.model.entity.object.RecordStatus;
import br.com.simnetwork.BotByCasseb.model.repository.EntityRepository;
import br.com.simnetwork.BotByCasseb.model.repository.EntitySchemaRepository;
import br.com.simnetwork.BotByCasseb.model.repository.FieldRepository;
import br.com.simnetwork.BotByCasseb.model.repository.FieldSchemaRepository;
import br.com.simnetwork.BotByCasseb.model.repository.RecordRepository;

@Service("entityService")
public class EntityServiceImpl implements EntityService {

	@Autowired
	private EntityRepository entityRepo;
	@Autowired
	private EntitySchemaRepository entitySchemaRepo;
	@Autowired
	private BotUserService botUserService;
	@Autowired
	private FieldSchemaRepository fieldSchemaRepo;
	@Autowired
	private DialogSchemaService dialogSchemaService;
	@Autowired
	private ContextService contextService;
	@Autowired
	private RecordRepository recordRepo;
	@Autowired
	private DecisionService decisionService;
	@Autowired
	private FieldRepository fieldRepo;

	public void synchronizeStaticEntities() {
		synchronizeBotUserEntity();
		synchronizeDialogSchemaEntity();
		
		for(String entitySchemaName : contextService.getEntitySchemasBeanDefinitionNames(true)) {
			EntitySchema entitySchema = (EntitySchema) contextService.getObjectBean(entitySchemaName, EntitySchema.class);
			entitySchemaRepo.save(entitySchema);
		}
		
	}
	
	public void synchronizeStaticEntitiesTest() {
		for(String entitySchemaName : contextService.getEntitySchemasBeanDefinitionNames(false)) {
			EntitySchema entitySchema = (EntitySchema) contextService.getObjectBean(entitySchemaName, EntitySchema.class);
			entitySchemaRepo.save(entitySchema);
		}
	}
	
	
	public Record findByKey(String entityName, String key) {
		return recordRepo.findByEntityNameAndChave(entityName, key);
	}
	
	
	public List<Record> findByFields(String entityName, Map<String,String> decisions){
		decisions = decisionService.getDecisionsFilter(decisions, "query:");
		HashSet<Record> result = new HashSet<Record>();
		if(!decisions.isEmpty()) {
			for(String key : decisions.keySet()) {
				List<Field> fields = fieldRepo.findByNomeFieldAndValue(key, decisions.get(key));
				for(Field field : fields) {
					Record record = findByKey(entityName,field.getKey());
					result.add(record);
				}
			}
			List<Record> resultRecord = new LinkedList<Record>(result);
			return resultRecord;
		}else {
			return recordRepo.findByEntityName(entityName);
		}
		
	}
	
	public void deleteByKey(String entityName, String key) {
		recordRepo.delete(findByKey(entityName,key));
	}

	public Object getValue(Record record, String fieldName) {
		return record.getCampos().get(fieldName).getValue();
	}

	public String getType(Record record, String fieldName) {
		return record.getCampos().get(fieldName).getFieldSchema().getTipo();
	}

	public String getType(String entityName, String fieldName) {
		return entityRepo.findOne(entityName).getEntitySchema().getCampos().get(fieldName).getTipo();
	}

	public String getEntity(Record record) {
		return record.getEntityName();
	}

	public Boolean setValue(Record record, String fieldName, Object newValue) {
		if(validateValue(record,fieldName,newValue)) {
			Field field = record.getCampos().get(fieldName);
			field.setValue(newValue);
			fieldRepo.save(field);
			return true;
		}else {
			return false;
		}
	}

	public Boolean validateValue(Record record, String fieldName, Object newValue) {
		String type = getType(record, fieldName);
		if(newValue instanceof String && type.equals("String")
		|| newValue instanceof Boolean && type.equals("Boolean")
		|| newValue instanceof Integer && type.equals("Integer")
		|| newValue instanceof DialogSchema && type.equals("DialogSchema")
		|| newValue instanceof BotUser && type.equals("BotUser")
		) {
			return true;
		}else {
			return false;
		}
		
	}

	public String getString(Record record, String fieldName) {
		String valueString;
		try {
			valueString = (String) getValue(record, fieldName);
		} catch (Exception e) {
			return null;
		}
		return valueString;
	}

	public Integer getInteger(Record record, String fieldName) {
		Integer valueInteger;
		try {
			Object value = getValue(record, fieldName);
			valueInteger = Integer.parseInt(value.toString());
			return valueInteger;
		} catch (Exception e) {
			return null;
		}
		
	}

	public DialogSchema getDialogSchema(Record record, String fieldName) {
		DialogSchema valueDialogSchema;
		try {
			valueDialogSchema = (DialogSchema) getValue(record, fieldName);
		} catch (Exception e) {
			return null;
		}
		return valueDialogSchema;
	}

	public BotUser getBotUser(Record record, String fieldName) {
		BotUser valueBotUser;
		try {
			valueBotUser = (BotUser) getValue(record, fieldName);
		} catch (Exception e) {
			return null;
		}
		return valueBotUser;
	}
	
	public Record getRecord(Record record, String fieldName) {
		Record valueRecord;
		try {
			valueRecord = (Record) getValue(record, fieldName);
		} catch (Exception e) {
			return null;
		}
		return valueRecord;
	}
	
	public Boolean getBoolean(Record record, String fieldName) {
		Boolean valueBoolean;
		try {
			valueBoolean = Boolean.valueOf(getValue(record, fieldName).toString()) ;
		} catch (Exception e) {
			return null;
		}
		return valueBoolean;
	}
	
	@SuppressWarnings("unchecked")
	public List<Record> getRecordList(Record record, String fieldName) {
		List<Record> valueRecordList;
		try {
			valueRecordList = (List<Record>) getValue(record, fieldName);
		} catch (Exception e) {
			return null;
		}
		return valueRecordList;
	}

	public Record findByKey(List<Record> records, Object key) {
		for(Record record : records) {
			if(record.getChave().equals(key)) {
				return record;
			}
		}
		return null;
	}
	

	public void synchronizeBotUserEntity() {
		entitySchemaRepo.delete("botUser");
		entityRepo.delete("botUser");

		Map<String, FieldSchema> fields = new HashMap<String, FieldSchema>();

		fields.put("id", new FieldSchema("id", "Integer", true));
		fields.put("data", new FieldSchema("data", "BotUser", true));

		EntitySchema entitySchema = new EntitySchema("botUser", "id");
		entitySchema.setCampos(fields);
		entitySchemaRepo.save(entitySchema);

		Entity entity = new Entity("botUser", entitySchema);
		entityRepo.save(entity);

		for (BotUser botUser : botUserService.locateAllBotUsers()) {

			Map<String, Object> content = new HashMap<String, Object>();
			content.putIfAbsent("id", botUser.getId());
			content.putIfAbsent("data", botUser);

			insertRecord("botUser", content);
			
			content = new HashMap<String, Object>();
			content.putIfAbsent("idTelegram", botUser.getId());
			content.putIfAbsent("botUser", findByKey("botUser",botUser.getId().toString()));
			insertRecord("Usuario",content);
		}
		

	}

	private void synchronizeDialogSchemaEntity() {

		entitySchemaRepo.delete("dialogSchema");
		entityRepo.delete("dialogSchema");

		Map<String, FieldSchema> fields = new HashMap<String, FieldSchema>();

		fields.put("nomeSchema", new FieldSchema("nomeSchema", "String", true));
		fields.put("data", new FieldSchema("data", "DialogSchema", true));

		EntitySchema entitySchema = new EntitySchema("dialogSchema", "nomeSchema");
		entitySchema.setCampos(fields);
		entitySchemaRepo.save(entitySchema);

		Entity entity = new Entity("dialogSchema", entitySchema);
		entityRepo.save(entity);

		for (DialogSchema dialogSchema : dialogSchemaService.findAllDialogSchema()) {

			Map<String, Object> content = new HashMap<String, Object>();
			content.putIfAbsent("nomeSchema", dialogSchema.getNomeSchema());
			content.putIfAbsent("dialogSchema", dialogSchema);

			insertRecord("dialogSchema", content);
		}

	}

	public RecordStatus insertRecord(String entityName, Map<String, Object> content) {

		EntitySchema entitySchema = entitySchemaRepo.findOne(entityName);
		if (entitySchema == null) {
			return RecordStatus.ENTIDADE_INEXISTENTE;
		}

		String key;

		if (content.get(entitySchema.getChave()) == null) {
			return RecordStatus.CHAVE_NULL;
		}else {
			key = content.get(entitySchema.getChave()).toString();
		}

		for (FieldSchema fieldSchema : fieldSchemaRepo.findByNotNull(true)) {
			if (content.get(fieldSchema.getNomeField()) == null) {
				return RecordStatus.CAMPO_OBRIGATORIO_NULL;
			}
		}
		
		Entity entity = entityRepo.findOne(entityName);
		if(entity == null) {
			entity = new Entity(entityName,entitySchema);
		}
		
		Map<String, Field> fieldsRecord = new HashMap<String, Field>();
		for (FieldSchema fieldSchema : entitySchema.getCampos().values()) {

			Object fieldValue = content.get(fieldSchema.getNomeField());
			if (fieldValue != null) {
				Field field = new Field(fieldSchema.getNomeField(), fieldValue, fieldSchema, key);
				fieldRepo.save(field);
				fieldsRecord.put(fieldSchema.getNomeField(),field);
			}else if(fieldSchema.getDefaultValue() != null) {
				Field field = new Field(fieldSchema.getNomeField(), fieldSchema.getDefaultValue(), fieldSchema, key);
				fieldRepo.save(field);
				fieldsRecord.put(fieldSchema.getNomeField(),field);
			}
		}

		Record record = new Record(key, entityName);
		record.setCampos(fieldsRecord);
		recordRepo.save(record);
		entity.setItem(key, record);
		entityRepo.save(entity);

		return RecordStatus.SUCESSO;

	}

	@Override
	public RecordStatus insertRecordString(String entity, Map<String, String> decisions) {
		Map<String,Object> content = new HashMap<String,Object>();
		
		for(String key : decisions.keySet()) {
			content.put(key, decisions.get(key));
		}
		
		return insertRecord(entity,content);
	}



}
