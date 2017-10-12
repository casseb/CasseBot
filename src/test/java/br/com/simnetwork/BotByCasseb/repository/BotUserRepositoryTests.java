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
import br.com.simnetwork.BotByCasseb.model.entity.object.BotUser;
import br.com.simnetwork.BotByCasseb.model.entity.object.Entity;
import br.com.simnetwork.BotByCasseb.model.repository.BotUserRepository;
import br.com.simnetwork.BotByCasseb.model.repository.EntityRepository;

@RunWith(SpringRunner.class)
@DataMongoTest
public class BotUserRepositoryTests {
	
	@Autowired
	private BotUserRepository botUserRepo;
	private Integer ID;
	
	@Before
	public void before(){
		Message message = TestSupport.createMessage();
		ID = message.from().id();
		BotUser botUser = new BotUser(message.from());
		botUserRepo.save(botUser);
	}
	
	@After
	public void after(){
		botUserRepo.delete(ID);
	}
	
	@Test
	public void saveTest() {
		assertNotNull(botUserRepo.findOne(ID));
	}
	
}
