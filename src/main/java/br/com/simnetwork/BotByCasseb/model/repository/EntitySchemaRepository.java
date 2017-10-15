package br.com.simnetwork.BotByCasseb.model.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.simnetwork.BotByCasseb.model.entity.object.EntitySchema;
import br.com.simnetwork.BotByCasseb.model.entity.object.FieldSchema;

public interface EntitySchemaRepository extends MongoRepository<EntitySchema,String>{

	public List<FieldSchema> findByCamposNotNull(Boolean notNull);
	
}
