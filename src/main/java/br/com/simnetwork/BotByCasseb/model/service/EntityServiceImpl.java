package br.com.simnetwork.BotByCasseb.model.service;

import java.util.HashMap;
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
import br.com.simnetwork.BotByCasseb.model.repository.FieldSchemaRepository;

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
	
	
	public Record findByKey(String entityName, Object key) {
		
		Entity entity = entityRepo.findOne(entityName);
		for(Record record : entity.getItens().values()) {
			if(record.getChave().equals(key)) {
				return record; 
			}
		}
		return null;
		
	}
	
	public void deleteByKey(String entityName, Object key) {
		Entity entity = entityRepo.findOne(entityName);
		Map<Object,Record> itens = new HashMap<Object,Record>();
		for(Record record : entity.getItens().values()) {
			if(!record.getChave().equals(key)) {
				itens.put(record.getChave(), record);
			}
		}
		entity.setItens(itens);
		entityRepo.save(entity);
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
			record.getCampos().put(fieldName, field);
			Entity entity = entityRepo.findOne(getEntity(record));
			entity.setItem(record.getChave(), record);
			entityRepo.save(entity);
			return true;
		}else {
			return false;
		}
	}

	public Boolean validateValue(Record record, String fieldName, Object newValue) {
		String type = getType(record, fieldName);
		if(newValue instanceof String && type.equals("String")
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

	private void synchronizeBotUserEntity() {
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

		Object key = content.get(entitySchema.getChave());

		if (key == null) {
			return RecordStatus.CHAVE_NULL;
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
				fieldsRecord.put(fieldSchema.getNomeField(),
						new Field(fieldSchema.getNomeField(), fieldValue, fieldSchema));
			}
		}

		Record record = new Record(key, entityName);
		record.setCampos(fieldsRecord);
		entity.setItem(key, record);
		entityRepo.save(entity);

		return RecordStatus.SUCESSO;

	}



}
