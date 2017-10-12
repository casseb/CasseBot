package br.com.simnetwork.BotByCasseb.service;

import static org.junit.Assert.*;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.simnetwork.BotByCasseb.model.repository.DialogSchemaRepository;
import br.com.simnetwork.BotByCasseb.model.service.ContextService;
import br.com.simnetwork.BotByCasseb.model.service.ContextServiceImpl;
import br.com.simnetwork.BotByCasseb.model.service.DialogSchemaService;
import br.com.simnetwork.BotByCasseb.model.service.DialogSchemaServiceImpl;


@RunWith(SpringRunner.class)
@DataMongoTest
public class DialogSchemaServiceTests {

	@TestConfiguration
    static class DialogSchemaServiceTestContextConfiguration {
  
        @Bean
        public DialogSchemaService dialogSchemaService() {
            return new DialogSchemaServiceImpl();
        }
        
        @Bean
        public ContextService contextService() {
            return new ContextServiceImpl();
        }
    }

	@Autowired
	private DialogSchemaRepository dialogSchemaRepo;
	@Autowired
	private DialogSchemaService dialogSchemaService;

	@Before
	public void before() {
		dialogSchemaService.synchronizeDialogSchema("|D|DialogSchemaTest|");
	}

	@After
	public void after() {
		dialogSchemaRepo.delete("|D|DialogSchemaTest|");
	}

	@Test
	public void sycronizeDialogSchemaTest1() {
		assertNotNull("Conferindo bean no banco",dialogSchemaRepo.findOne("|D|DialogSchemaTest|"));
	}
	
	@Test
	public void sycronizeDialogSchemaTest2() {
		assertFalse("Conferindo passos definidos",dialogSchemaRepo.findOne("|D|DialogSchemaTest|").getSteps().isEmpty());
	}
	
	@Test
	public void sycronizeDialogSchemaTest3() {
		assertFalse("Conferindo se as opções estão vazias",dialogSchemaRepo.findOne("|D|DialogSchemaTest|").getSteps().get(1).getKeyboardOptions().isEmpty());
	}
	
	@Test
	public void sycronizeDialogSchemaTest4() {
		assertNotNull("Conferindo carregamento do keyboard",dialogSchemaRepo.findOne("|D|DialogSchemaTest|").getSteps().get(1).getKeyboard());
	}
	

}
