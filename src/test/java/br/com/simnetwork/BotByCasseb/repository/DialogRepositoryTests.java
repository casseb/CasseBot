package br.com.simnetwork.BotByCasseb.repository;

import static org.junit.Assert.*;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.gson.JsonObject;
import com.pengrad.telegrambot.BotUtils;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;

import br.com.simnetwork.BotByCasseb.TestSupport;
import br.com.simnetwork.BotByCasseb.model.entity.dialog.structure.Dialog;
import br.com.simnetwork.BotByCasseb.model.entity.dialog.structure.DialogSchema;
import br.com.simnetwork.BotByCasseb.model.entity.object.BotUser;
import br.com.simnetwork.BotByCasseb.model.entity.object.Entity;
import br.com.simnetwork.BotByCasseb.model.repository.BotUserRepository;
import br.com.simnetwork.BotByCasseb.model.repository.DialogRepository;
import br.com.simnetwork.BotByCasseb.model.repository.DialogSchemaRepository;
import br.com.simnetwork.BotByCasseb.model.repository.EntityRepository;

@RunWith(SpringRunner.class)
@DataMongoTest
public class DialogRepositoryTests {
	
	@Autowired
	private DialogRepository dialogRepo;
	@Autowired
	private DialogSchemaRepository dialogSchemaRepo;
	BotUser botUser;
	String NOMESCHEMA = "TestSchema";
	
	@Before
	public void before() throws JSONException {
		Message message = TestSupport.createMessage();
		botUser = new BotUser(message.from());
		DialogSchema dialogSchema = new DialogSchema(NOMESCHEMA);
		dialogSchemaRepo.save(dialogSchema);
		Dialog dialog = new Dialog(botUser,dialogSchemaRepo.findOne(NOMESCHEMA));
		dialogRepo.save(dialog);
	}
	
	@After
	public void after() {
		dialogRepo.delete(botUser);
	}
	
	@Test
	public void saveTest() {
		assertNotNull(botUser);
	}
	
}
