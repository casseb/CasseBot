package br.com.simnetwork.BotByCasseb.model.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.simnetwork.BotByCasseb.model.entity.object.EntitySchema;
import br.com.simnetwork.BotByCasseb.model.entity.object.FieldSchema;

public interface FieldSchemaRepository extends MongoRepository<FieldSchema,String> {

	public List<FieldSchema> findByNotNull(boolean notNull);
	
}
