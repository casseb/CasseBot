package br.com.simnetwork.BotByCasseb.repository;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.simnetwork.BotByCasseb.model.entity.object.DynamicEntity;
import br.com.simnetwork.BotByCasseb.model.repository.DynamicEntityRepository;

@RunWith(SpringRunner.class)
@DataMongoTest
public class DynamicEntityRepositoryTests {

	String nomeEntity = "EntidadeTeste";
	
	@Autowired
	private DynamicEntityRepository dynamicEntityRepo;
	
	@Before
	public void before() {
		DynamicEntity dynamicEntity = new DynamicEntity(nomeEntity);
		dynamicEntityRepo.save(dynamicEntity);
	}
	
	@After
	public void after() {
		dynamicEntityRepo.delete(nomeEntity);
	}
	
	@Test
	public void saveTest() {
		assertNotNull(dynamicEntityRepo.findOne(nomeEntity));
	}
	
}
