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

import br.com.simnetwork.BotByCasseb.TestSupport;
import br.com.simnetwork.BotByCasseb.model.entity.object.BotUser;
import br.com.simnetwork.BotByCasseb.model.repository.BotUserRepository;
import br.com.simnetwork.BotByCasseb.model.repository.DialogRepository;
import br.com.simnetwork.BotByCasseb.model.repository.DialogSchemaRepository;
import br.com.simnetwork.BotByCasseb.model.service.BotUserService;
import br.com.simnetwork.BotByCasseb.model.service.BotUserServiceImpl;
import br.com.simnetwork.BotByCasseb.model.service.ContextService;
import br.com.simnetwork.BotByCasseb.model.service.ContextServiceImpl;
import br.com.simnetwork.BotByCasseb.model.service.DialogSchemaService;
import br.com.simnetwork.BotByCasseb.model.service.DialogSchemaServiceImpl;
import br.com.simnetwork.BotByCasseb.model.service.DialogService;
import br.com.simnetwork.BotByCasseb.model.service.DialogServiceImpl;
import br.com.simnetwork.BotByCasseb.model.service.DialogStepSchemaService;
import br.com.simnetwork.BotByCasseb.model.service.DialogStepSchemaServiceImpl;
import br.com.simnetwork.BotByCasseb.model.service.DynamicListService;
import br.com.simnetwork.BotByCasseb.model.service.DynamicListServiceImpl;
import br.com.simnetwork.BotByCasseb.model.service.EntityService;
import br.com.simnetwork.BotByCasseb.model.service.EntityServiceImpl;
import br.com.simnetwork.BotByCasseb.model.service.KeyboardService;
import br.com.simnetwork.BotByCasseb.model.service.KeyboardServiceImpl;


@RunWith(SpringRunner.class)
@DataMongoTest
public class DialogServiceTests {

	@TestConfiguration
    static class DialogServiceTestContextConfiguration {
  
		@Bean
        public DialogService dialogService() {
            return new DialogServiceImpl();
        }
		
		@Bean
        public DialogStepSchemaService dialogStepSchemaService() {
            return new DialogStepSchemaServiceImpl();
        }
		
		@Bean
        public DialogSchemaService dialogSchemaService() {
            return new DialogSchemaServiceImpl();
        }
        
        @Bean
        public ContextService contextService() {
            return new ContextServiceImpl();
        }
        
        @Bean
        public BotUserService botUserService() {
            return new BotUserServiceImpl();
        }
        
        @Bean
        public KeyboardService keyboardService() {
            return new KeyboardServiceImpl();
        }
        
        @Bean
        public DynamicListService dynamicListService() {
            return new DynamicListServiceImpl();
        }
        
        @Bean
		public EntityService entityService() {
			return new EntityServiceImpl();
		}
    }

	@Autowired
	private DialogSchemaRepository dialogSchemaRepo;
	@Autowired
	private DialogSchemaService dialogSchemaService;
	@Autowired
	private DialogService dialogService;
	@Autowired
	private DialogRepository dialogRepo;
	@Autowired
	private BotUserRepository botUserRepo;

	@Before
	public void before() {
		dialogSchemaService.synchronizeDialogSchema("|D|DialogSchemaTest|");
		dialogService.createDialog(TestSupport.createMessage().from(), (dialogSchemaRepo.findOne("|D|DialogSchemaTest|")));
	}

	@After
	public void after() {
		dialogSchemaRepo.delete("|D|DialogSchemaTest|");
		dialogRepo.delete(new BotUser(TestSupport.createMessage().from()));
		botUserRepo.delete(botUserRepo.findOne(TestSupport.createMessage().from().id()));
	}

	@Test
	public void createDialogTest() {
		assertNotNull(dialogRepo.findOne(new BotUser(TestSupport.createMessage().from())));
	}
	
	@Test
	public void resetAllDialogsTest() {
		dialogService.resetAllDialogs();
		assertTrue(dialogRepo.findAll().isEmpty());
	}
	

}
