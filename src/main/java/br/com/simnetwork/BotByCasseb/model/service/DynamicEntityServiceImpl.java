package br.com.simnetwork.BotByCasseb.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.simnetwork.BotByCasseb.model.entity.object.DynamicEntity;
import br.com.simnetwork.BotByCasseb.model.repository.DynamicEntityRepository;

@Service("dynamicEntityService")
public class DynamicEntityServiceImpl implements DynamicEntityService{

	@Autowired
	private DynamicEntityRepository dynamicEntityRepo;
	
	@Override
	public boolean createDynamicEntity(String nomeEntity) {
		if(!dynamicEntityRepo.exists(nomeEntity)) {
			DynamicEntity entity = new DynamicEntity(nomeEntity);
			dynamicEntityRepo.save(entity);
			return true;
		}else {
			return false;
		}
	}
	
}
