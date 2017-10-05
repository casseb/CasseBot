package br.com.simnetwork.BotByCasseb.model.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.simnetwork.BotByCasseb.model.entity.dialog.structure.DialogSchema;

public interface DialogSchemaRepository extends MongoRepository<DialogSchema, String>{
	
}
