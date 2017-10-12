package br.com.simnetwork.BotByCasseb.service;

import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.simnetwork.BotByCasseb.model.repository.EntityRepository;
import br.com.simnetwork.BotByCasseb.model.service.EntityService;
import br.com.simnetwork.BotByCasseb.model.service.EntityServiceImpl;

@RunWith(SpringRunner.class)
@DataMongoTest
public class DynamicEntityServiceTests {

	@TestConfiguration
    static class DynamicEntityServiceTestContextConfiguration {
  
        @Bean
        public EntityService dynamicEntityService() {
            return new EntityServiceImpl();
        }
    }
	
	String nomeEntity = "EntidadeTeste";

	@Autowired
	private EntityRepository dynamicEntityRepo;
	@Autowired
	private EntityService dynamicEntityService;

	@Before
	public void before() {
		dynamicEntityService.createDynamicEntity(nomeEntity);
	}

	@After
	public void after() {
		dynamicEntityRepo.delete(dynamicEntityRepo.findOne(nomeEntity));
	}

	@Test
	public void createDynamicEntityTest() {
		assertNotNull(dynamicEntityRepo.findOne(nomeEntity));
	}

}
