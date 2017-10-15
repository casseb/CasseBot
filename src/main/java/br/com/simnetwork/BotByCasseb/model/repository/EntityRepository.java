package br.com.simnetwork.BotByCasseb.model.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import br.com.simnetwork.BotByCasseb.model.entity.object.Entity;
import br.com.simnetwork.BotByCasseb.model.entity.object.Record;

public interface EntityRepository extends MongoRepository<Entity, String>{
	
	public List<Record> findByItensCamposNomeField(String nomeField);
	public Record findByItens(Object key);
	
}
