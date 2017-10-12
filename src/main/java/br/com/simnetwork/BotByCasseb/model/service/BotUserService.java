package br.com.simnetwork.BotByCasseb.model.service;

import com.pengrad.telegrambot.model.Contact;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.User;

import br.com.simnetwork.BotByCasseb.model.entity.object.BotUser;

public interface BotUserService {

	public BotUser createBotUser(User user);
	public BotUser locateBotUser(Integer id);
	public void updateBotUserContact(BotUser botUser, Contact contact);
	
}
