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

import br.com.simnetwork.BotByCasseb.model.entity.dialog.structure.DialogSchema;
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
public class ContextServiceTests {

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
	private ContextService contextService;

	@Test
	public void getDialogSchemasBeanDefinitionNamesTest() {
		assertFalse(contextService.getDialogSchemasBeanDefinitionNames(false).isEmpty());
	}

	@Test
	public void getStepSchemasBeanDefinitionNamesTest() {
		assertFalse(contextService.getStepSchemasBeanDefinitionNames(false).isEmpty());
	}

	@Test
	public void getDialogSchemasBeanDefinitionCountTest() {
		assertNotEquals("Testando se sendo retornado pelo menos 1 registro", 0,
				contextService.getDialogSchemasBeanDefinitionCount(false));
	}

	@Test
	public void getStepSchemasBeanDefinitionCountTest() {
		assertNotEquals(0, contextService.getStepSchemasBeanDefinitionCount(false));
	}

	@Test
	public void compareNumberDialogSchemasBeanDefinitionTest() {
		assertEquals(contextService.getDialogSchemasBeanDefinitionNames(false).size(),
				contextService.getDialogSchemasBeanDefinitionCount(false));
	}

	@Test
	public void compareNumberStepsSchemasBeanDefinitionTest() {
		assertEquals(contextService.getStepSchemasBeanDefinitionNames(false).size(),
				contextService.getStepSchemasBeanDefinitionCount(false));
	}

	@Test
	public void getObjectBeanTest() {
		assertNotNull(contextService.getObjectBean("|D|DialogSchemaTest|", DialogSchema.class));
		assertNull(contextService.getObjectBean("Sem Bean", DialogSchema.class));
	}

}
