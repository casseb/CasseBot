package br.com.simnetwork.BotByCasseb.service;

import static org.junit.Assert.assertNotNull;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.pengrad.telegrambot.BotUtils;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;

import br.com.simnetwork.BotByCasseb.TestSupport;
import br.com.simnetwork.BotByCasseb.model.repository.BotUserRepository;
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
public class BotUserServiceTests {

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
	private BotUserRepository botUserRepo;
	@Autowired
	private BotUserService botUserService;
	private Integer ID;

	@Before
	public void before() {
		Message message = TestSupport.createMessage();
		ID = message.from().id();
		botUserService.createBotUser(message.from());
	}

	@After
	public void after() {
		botUserRepo.delete(botUserRepo.findOne(ID));
	}

	@Test
	public void createDynamicEntityTest() {
		assertNotNull(botUserRepo.findOne(ID));
		assertNotNull(botUserService.locateBotUser(ID));
	}

}
