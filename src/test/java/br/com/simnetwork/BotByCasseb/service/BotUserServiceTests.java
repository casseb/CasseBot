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

@RunWith(SpringRunner.class)
@DataMongoTest
public class BotUserServiceTests {

	@TestConfiguration
    static class BotUserTestContextConfiguration {
  
        @Bean
        public BotUserService botUserService() {
            return new BotUserServiceImpl();
        }
    }

	@Autowired
	private BotUserRepository botUserRepo;
	@Autowired
	private BotUserService botUserService;
	private Integer ID;

	@Before
	public void before(){
		Message message = TestSupport.createMessage();
		ID = message.from().id();
		botUserService.createBotUser(message);
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
