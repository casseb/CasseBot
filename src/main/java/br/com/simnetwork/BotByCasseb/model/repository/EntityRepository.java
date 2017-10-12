package br.com.simnetwork.BotByCasseb.model.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import br.com.simnetwork.BotByCasseb.model.entity.object.Entity;

public interface EntityRepository extends MongoRepository<Entity, String>{
	
}
