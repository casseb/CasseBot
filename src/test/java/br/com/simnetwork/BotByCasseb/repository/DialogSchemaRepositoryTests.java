package br.com.simnetwork.BotByCasseb.repository;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.simnetwork.BotByCasseb.model.entity.dialog.structure.DialogSchema;
import br.com.simnetwork.BotByCasseb.model.entity.object.Entity;
import br.com.simnetwork.BotByCasseb.model.repository.DialogSchemaRepository;
import br.com.simnetwork.BotByCasseb.model.repository.EntityRepository;

@RunWith(SpringRunner.class)
@DataMongoTest
public class DialogSchemaRepositoryTests {
	
	@Autowired
	private DialogSchemaRepository dialogSchemaRepo;
	
	String NOMESCHEMA = "TestSchema";
	
	@Before
	public void before() {
		DialogSchema dialogSchema = new DialogSchema(NOMESCHEMA);
		dialogSchemaRepo.save(dialogSchema);
	}
	
	@After
	public void after() {
		dialogSchemaRepo.delete(NOMESCHEMA);
	}
	
	@Test
	public void saveTest() {
		assertNotNull(dialogSchemaRepo.findOne(NOMESCHEMA));
	}
	
	
}
