package br.com.simnetwork.BotByCasseb.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pengrad.telegrambot.model.Contact;
import com.pengrad.telegrambot.model.Message;

import br.com.simnetwork.BotByCasseb.model.entity.object.BotUser;
import br.com.simnetwork.BotByCasseb.model.repository.BotUserRepository;

@Service("botUserService")
public class BotUserServiceImpl implements BotUserService{

	@Autowired
	private BotUserRepository botUserRepo;
	
	@Override
	public BotUser createBotUser(Message message) {
		if(!botUserRepo.exists(message.from().id())){
			BotUser user = new BotUser(message.from());
			botUserRepo.save(user);
			return user;
		}else {
			return botUserRepo.findOne(message.from().id());
		}
	}

	@Override
	public BotUser locateBotUser(Integer id) {
		return botUserRepo.findOne(id);
	}

	@Override
	public void updateBotUserContact(BotUser botUser, Contact contact) {
		botUser.setContact(contact);
		botUserRepo.save(botUser);
	}

}
