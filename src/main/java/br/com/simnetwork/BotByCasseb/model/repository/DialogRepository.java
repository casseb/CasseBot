package br.com.simnetwork.BotByCasseb.model.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.simnetwork.BotByCasseb.model.entity.dialog.structure.Dialog;
import br.com.simnetwork.BotByCasseb.model.entity.object.BotUser;

public interface DialogRepository extends MongoRepository<Dialog, BotUser>{

	public Dialog findByBotUserId(Integer id);
	
}
