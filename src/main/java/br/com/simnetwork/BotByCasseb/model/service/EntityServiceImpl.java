package br.com.simnetwork.BotByCasseb.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.simnetwork.BotByCasseb.model.entity.object.Entity;
import br.com.simnetwork.BotByCasseb.model.repository.EntityRepository;

@Service("dynamicEntityService")
public class EntityServiceImpl implements EntityService{

	@Autowired
	private EntityRepository dynamicEntityRepo;
	
	@Override
	public boolean createDynamicEntity(String nomeEntity) {
		if(!dynamicEntityRepo.exists(nomeEntity)) {
			Entity entity = new Entity(nomeEntity);
			dynamicEntityRepo.save(entity);
			return true;
		}else {
			return false;
		}
	}
	
}
