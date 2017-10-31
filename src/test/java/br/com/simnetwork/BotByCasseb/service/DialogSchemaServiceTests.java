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
import br.com.simnetwork.BotByCasseb.model.service.BotUserService;
import br.com.simnetwork.BotByCasseb.model.service.BotUserServiceImpl;
import br.com.simnetwork.BotByCasseb.model.service.ContextService;
import br.com.simnetwork.BotByCasseb.model.service.ContextServiceImpl;
import br.com.simnetwork.BotByCasseb.model.service.DecisionService;
import br.com.simnetwork.BotByCasseb.model.service.DecisionServiceImpl;
import br.com.simnetwork.BotByCasseb.model.service.DialogSchemaService;
import br.com.simnetwork.BotByCasseb.model.service.DialogSchemaServiceImpl;
import br.com.simnetwork.BotByCasseb.model.service.DialogService;
import br.com.simnetwork.BotByCasseb.model.service.DialogServiceImpl;
import br.com.simnetwork.BotByCasseb.model.service.DialogStepSchemaServiceImpl;
import br.com.simnetwork.BotByCasseb.model.service.DynamicListService;
import br.com.simnetwork.BotByCasseb.model.service.DynamicListServiceImpl;
import br.com.simnetwork.BotByCasseb.model.service.EntityService;
import br.com.simnetwork.BotByCasseb.model.service.EntityServiceImpl;
import br.com.simnetwork.BotByCasseb.model.service.KeyboardService;
import br.com.simnetwork.BotByCasseb.model.service.KeyboardServiceImpl;
import br.com.simnetwork.BotByCasseb.model.service.DialogStepSchemaService;

@RunWith(SpringRunner.class)
@DataMongoTest
public class DialogSchemaServiceTests {

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

		@Bean
		public DecisionService decisionService() {
			return new DecisionServiceImpl();
		}

	}

	@Autowired
	private DialogSchemaRepository dialogSchemaRepo;
	@Autowired
	private DialogSchemaService dialogSchemaService;
	@Autowired
	private DialogStepSchemaService dialogStepSchemaService;

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
		assertNotNull("Conferindo bean no banco", dialogSchemaRepo.findOne("|D|DialogSchemaTest|"));
	}

	@Test
	public void sycronizeDialogSchemaTest2() {
		assertFalse("Conferindo passos definidos",
				dialogSchemaRepo.findOne("|D|DialogSchemaTest|").getSteps().isEmpty());
	}

	@Test
	public void sycronizeDialogSchemaTest3() {
		assertFalse("Conferindo se as opções estão vazias",
				dialogSchemaRepo.findOne("|D|DialogSchemaTest|").getSteps().get(1).getKeyboardOptions().isEmpty());
	}

	@Test
	public void sycronizeDialogSchemaTest4() {
		assertNotNull("Conferindo carregamento do keyboard", dialogStepSchemaService
				.getKeyboard(dialogSchemaRepo.findOne("|D|DialogSchemaTest|").getSteps().get(1)));
	}

}