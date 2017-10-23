package br.com.simnetwork.BotByCasseb.model.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.simnetwork.BotByCasseb.model.entity.object.Field;

public interface FieldRepository extends MongoRepository<Field,String>{

	public List<Field> findByNomeFieldAndValue(String nomeField, Object value);

	
}
