package br.com.simnetwork.BotByCasseb.model.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.simnetwork.BotByCasseb.model.entity.object.FieldSchema;

public interface FieldSchemaRepository extends MongoRepository<FieldSchema, String>{

}
