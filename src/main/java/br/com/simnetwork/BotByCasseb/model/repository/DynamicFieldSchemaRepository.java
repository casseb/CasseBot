package br.com.simnetwork.BotByCasseb.model.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.simnetwork.BotByCasseb.model.entity.object.DynamicFieldSchema;

public interface DynamicFieldSchemaRepository extends MongoRepository<DynamicFieldSchema, String>{

}
