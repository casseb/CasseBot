package br.com.simnetwork.BotByCasseb.model.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.simnetwork.BotByCasseb.model.entity.object.RecordSchema;

public interface RecordSchemaRepository extends MongoRepository<RecordSchema, ObjectId>{
	
	public List<RecordSchema> findByEntityName(String entityName);
	
	public RecordSchema findByEntityNameAndFieldName(String entityName, String fieldName);
	
	public List<RecordSchema> findByEntityNameAndNotNull(String entityName, boolean notNull);
	
	public List<RecordSchema> findByEntityNameAndIsKey(String entityName, boolean isKey);

}
