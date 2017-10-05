package br.com.simnetwork.BotByCasseb.model.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import br.com.simnetwork.BotByCasseb.model.entity.object.DynamicEntity;

public interface DynamicEntityRepository extends MongoRepository<DynamicEntity, String>{
	
}
