package br.com.simnetwork.BotByCasseb.model.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.simnetwork.BotByCasseb.model.entity.object.Record;

public interface RecordRepository extends MongoRepository<Record, String> {

	public Record findByEntityNameAndChave(String entityName, String chave);

	public List<Record> findByEntityNameAndCamposNomeFieldAndCamposValue(String entityName, String nomeField,
			Object value);
	
	public List<Record> findByEntityName(String entityName);

}
