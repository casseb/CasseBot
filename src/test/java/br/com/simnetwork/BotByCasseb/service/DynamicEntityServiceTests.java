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

import br.com.simnetwork.BotByCasseb.model.repository.DynamicEntityRepository;
import br.com.simnetwork.BotByCasseb.model.service.DynamicEntityService;
import br.com.simnetwork.BotByCasseb.model.service.DynamicEntityServiceImpl;

@RunWith(SpringRunner.class)
@DataMongoTest
public class DynamicEntityServiceTests {

	@TestConfiguration
    static class DynamicEntityServiceTestContextConfiguration {
  
        @Bean
        public DynamicEntityService dynamicEntityService() {
            return new DynamicEntityServiceImpl();
        }
    }
	
	String nomeEntity = "EntidadeTeste";

	@Autowired
	private DynamicEntityRepository dynamicEntityRepo;
	@Autowired
	private DynamicEntityService dynamicEntityService;

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
