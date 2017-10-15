package br.com.simnetwork.BotByCasseb.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.simnetwork.BotByCasseb.model.entity.object.RecordStatus;
import br.com.simnetwork.BotByCasseb.model.repository.EntityRepository;
import br.com.simnetwork.BotByCasseb.model.service.BotUserService;
import br.com.simnetwork.BotByCasseb.model.service.BotUserServiceImpl;
import br.com.simnetwork.BotByCasseb.model.service.ContextService;
import br.com.simnetwork.BotByCasseb.model.service.ContextServiceImpl;
import br.com.simnetwork.BotByCasseb.model.service.DialogSchemaService;
import br.com.simnetwork.BotByCasseb.model.service.DialogSchemaServiceImpl;
import br.com.simnetwork.BotByCasseb.model.service.EntityService;
import br.com.simnetwork.BotByCasseb.model.service.EntityServiceImpl;
import br.com.simnetwork.BotByCasseb.model.service.KeyboardService;
import br.com.simnetwork.BotByCasseb.model.service.KeyboardServiceImpl;

@RunWith(SpringRunner.class)
@DataMongoTest
public class EntityServiceTests {

	@TestConfiguration
	static class EntityServiceTestContextConfiguration {

		@Bean
		public BotUserService botUserService() {
			return new BotUserServiceImpl();
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
		public EntityService entityService() {
			return new EntityServiceImpl();
		}
		
		@Bean
        public KeyboardService keyboardService() {
            return new KeyboardServiceImpl();
        }

	}
	
	@Autowired
	private EntityRepository entityRepo;
	@Autowired
	private EntityService entityService;
	
	private Map<String,Object> content = new HashMap<String,Object>();

	
	@Before
	public void before(){
		entityService.synchronizeStaticEntitiesTest();
		content.put("id",1);
		content.put("nome", "Casseb");
		content.put("cpf", "325");
		entityService.insertRecord("TestEntity", content);
	}
	
	@After
	public void after() {
		entityRepo.delete("TestEntity");
	}
	
	@Test
	public void insertRecordTest() {
		assertEquals(RecordStatus.SUCESSO,entityService.insertRecord("TestEntity", content));
		content.remove("id");
		assertEquals(RecordStatus.CHAVE_NULL,entityService.insertRecord("TestEntity", content));
	}
	
	@Test
	public void findByKeyTest() {
		assertNotNull(entityService.findByKey("TestEntity", 1));
	}
	
	@Test
	public void deleteByKeyTest() {
		entityService.deleteByKey("TestEntity", 1);
		assertNull(entityService.findByKey("TestEntity", 1));
	}
	
	@Test
	public void getValueTest() {
		assertNotNull(entityService.getValue(entityService.findByKey("TestEntity", 1), "nome"));
	}
	
	@Test
	public void getTypeRecordTest() {
		assertEquals("String",entityService.getType(entityService.findByKey("TestEntity", 1),"nome"));
	}
	
	@Test
	public void getTypeEntityTest() {
		assertEquals("String",entityService.getType("TestEntity","nome"));
	}
	
	@Test
	public void getEntityTest() {
		assertEquals("TestEntity",entityService.getEntity(entityService.findByKey("TestEntity", 1)));
	}
	
	@Test
	public void setValueTest() {
		entityService.setValue(entityService.findByKey("TestEntity", 1), "nome", "NomeAlt");
		assertNotNull(entityService.getValue(entityService.findByKey("TestEntity", 1), "nome"));
		assertEquals("NomeAlt",entityService.getString(entityService.findByKey("TestEntity", 1), "nome"));
	}
	
	@Test
	public void validateValueTest() {
		String testeS = "teste";
		Integer testeI = 123;
		
		//assertTrue("Integer em Integer",entityService.validateValue(entityService.findByKey("TestEntity", 1), "id", testeI));
		//assertTrue("String em String",entityService.validateValue(entityService.findByKey("TestEntity", 1), "nome", testeS));
		
		assertFalse("Integer em String",entityService.validateValue(entityService.findByKey("TestEntity", 1), "id", testeS));
	}
	
	@Test
	public void getStringTest() {
		assertEquals("Casseb",entityService.getString(entityService.findByKey("TestEntity", 1), "nome"));
	}

}
