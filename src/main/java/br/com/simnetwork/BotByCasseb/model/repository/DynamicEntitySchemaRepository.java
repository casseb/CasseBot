package br.com.simnetwork.BotByCasseb.model.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.simnetwork.BotByCasseb.model.entity.object.DynamicEntitySchema;

public interface DynamicEntitySchemaRepository extends MongoRepository<DynamicEntitySchema,String>{

}
