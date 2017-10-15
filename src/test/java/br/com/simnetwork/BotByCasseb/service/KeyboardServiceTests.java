package br.com.simnetwork.BotByCasseb.service;

import static org.junit.Assert.assertNotNull;

import java.util.LinkedList;
import java.util.List;

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
import com.pengrad.telegrambot.model.request.Keyboard;

import br.com.simnetwork.BotByCasseb.TestSupport;
import br.com.simnetwork.BotByCasseb.model.repository.BotUserRepository;
import br.com.simnetwork.BotByCasseb.model.service.BotUserService;
import br.com.simnetwork.BotByCasseb.model.service.BotUserServiceImpl;
import br.com.simnetwork.BotByCasseb.model.service.KeyboardService;
import br.com.simnetwork.BotByCasseb.model.service.KeyboardServiceImpl;

@RunWith(SpringRunner.class)
@DataMongoTest
public class KeyboardServiceTests {

	@TestConfiguration
    static class KeyboardTestContextConfiguration {
  
        @Bean
        public KeyboardService keyboardService() {
            return new KeyboardServiceImpl();
        }
    }

	@Autowired
	private KeyboardService keyboardService;

	@Test
	public void getSimpleKeyboardTest() {
		List<String> options = new LinkedList<>();
		options.add("Teste A");
		options.add("Teste B");
		options.add("Teste C");
		Keyboard keyboard = keyboardService.getSimpleKeyboard(options);
		assertNotNull(keyboard);
	}

}
