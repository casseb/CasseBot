package br.com.simnetwork.BotByCasseb.model.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.simnetwork.BotByCasseb.model.entity.object.DynamicEntitySchema;

public interface DynamicEntitySchemaRepository extends MongoRepository<DynamicEntitySchema,String>{

}
