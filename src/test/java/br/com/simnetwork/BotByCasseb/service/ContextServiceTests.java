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
import br.com.simnetwork.BotByCasseb.model.service.ContextService;
import br.com.simnetwork.BotByCasseb.model.service.ContextServiceImpl;
import br.com.simnetwork.BotByCasseb.model.service.DialogSchemaService;
import br.com.simnetwork.BotByCasseb.model.service.DialogSchemaServiceImpl;


@RunWith(SpringRunner.class)
@DataMongoTest
public class ContextServiceTests {

	@TestConfiguration
    static class ContextServiceTestContextConfiguration {
  
        @Bean
        public ContextService contextService() {
            return new ContextServiceImpl();
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
		assertNotEquals("Testando se sendo retornado pelo menos 1 registro", 0, contextService.getDialogSchemasBeanDefinitionCount(false));
	}
	
	@Test
	public void getStepSchemasBeanDefinitionCountTest() {
		assertNotEquals(0, contextService.getStepSchemasBeanDefinitionCount(false));
	}
	
	@Test
	public void compareNumberDialogSchemasBeanDefinitionTest() {
		assertEquals(contextService.getDialogSchemasBeanDefinitionNames(false).size(), contextService.getDialogSchemasBeanDefinitionCount(false));
	}
	
	@Test
	public void compareNumberStepsSchemasBeanDefinitionTest() {
		assertEquals(contextService.getStepSchemasBeanDefinitionNames(false).size(), contextService.getStepSchemasBeanDefinitionCount(false));
	}
	
	@Test
	public void getObjectBeanTest() {
		assertNotNull(contextService.getObjectBean("|D|DialogSchemaTest|", DialogSchema.class));
		assertNull(contextService.getObjectBean("Sem Bean", DialogSchema.class));
	}

}
